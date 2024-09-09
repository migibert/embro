package com.migibert.embro.infrastructure.controller;

import com.migibert.embro.domain.model.Organization;
import com.migibert.embro.domain.service.OrganizationService;
import com.migibert.embro.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/organizations")
@AllArgsConstructor
public class OrganizationController {
    private final UserService userService;
    private final OrganizationService service;

    @GetMapping("/")
    public ResponseEntity list(Principal principal) {
        String userId = principal.getName();
        List<UUID> organizationsIds = userService.getOrganizations(userId);
        Iterable<Organization> organizations = service.findByIds(organizationsIds);
        return ResponseEntity.ok(organizations);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(Principal principal, @PathVariable("id") UUID id) {
        String userId = principal.getName();
        if(!userService.isAllowed(userId, id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Organization> organization = service.findById(id);
        if(organization.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(organization.get());
    }

    @PostMapping("/")
    public ResponseEntity create(Principal principal, @RequestBody Organization organization) {
        if(organization.id() != null) {
            return ResponseEntity.badRequest().body("id must be null");
        }
        String userId = principal.getName();
        Organization saved = service.create(organization);
        userService.allow(userId, saved.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(Principal principal, @PathVariable("id") UUID id) {
        String userId = principal.getName();
        if(!userService.isAllowed(userId, id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
