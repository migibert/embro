package com.migibert.embro.infrastructure.controller;

import com.migibert.embro.domain.model.Role;
import com.migibert.embro.domain.service.RoleService;
import com.migibert.embro.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/organizations/{organizationId}/roles")
@AllArgsConstructor
public class RoleController {

    private final RoleService service;
    private final UserService userService;

    @RequestMapping("/")
    public ResponseEntity list(Principal principal, @PathVariable("organizationId") UUID organizationId) {
        String userId = principal.getName();
        if (!userService.isAllowed(userId, organizationId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Iterable<Role> roles = service.findAll(organizationId);
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(Principal principal, @PathVariable("organizationId") UUID organizationId, @PathVariable("id") UUID id) {
        String userId = principal.getName();
        if (!userService.isAllowed(userId, organizationId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Role> role = service.findById(organizationId, id);
        if (role.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(role.get());
    }

    @PostMapping("/")
    public ResponseEntity create(Principal principal, @PathVariable("organizationId") UUID organizationId, @RequestBody Role role) {
        String userId = principal.getName();
        if (!userService.isAllowed(userId, organizationId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (role.id() != null) {
            return ResponseEntity.badRequest().body("id must be null");
        }
        Role saved = service.create(organizationId, role);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(Principal principal, @PathVariable("organizationId") UUID organizationId, @PathVariable("id") UUID id) {
        String userId = principal.getName();
        if (!userService.isAllowed(userId, organizationId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        service.delete(organizationId, id);
        return ResponseEntity.noContent().build();
    }
}