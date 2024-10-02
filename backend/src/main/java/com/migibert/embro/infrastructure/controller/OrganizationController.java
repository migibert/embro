package com.migibert.embro.infrastructure.controller;

import com.migibert.embro.domain.model.Organization;
import com.migibert.embro.domain.model.Role;
import com.migibert.embro.domain.model.User;
import com.migibert.embro.domain.service.OrganizationService;
import com.migibert.embro.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
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
        if(!userService.hasAnyRole(userId, id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Organization> organization = service.findById(id);
        if(organization.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(organization.get());
    }

    @GetMapping("/{organizationId}/users")
    public ResponseEntity getUsers(Principal principal, @PathVariable("organizationId") UUID organizationId) {
        String userId = principal.getName();
        if(!userService.hasAnyRole(userId, organizationId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Organization> organization = service.findById(organizationId);
        if(organization.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<User> result = userService.getUsersByOrganizationId(organizationId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{organizationId}/users")
    public ResponseEntity updateUser(Principal principal, @PathVariable("organizationId") UUID organizationId, @RequestBody User user) {
        String userId = principal.getName();
        if(!userService.hasRoleIn(userId, organizationId, Role.OWNER)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        userService.updateUser(organizationId, user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/")
    public ResponseEntity create(Principal principal, @RequestBody Organization organization) {
        if(organization.id() != null) {
            return ResponseEntity.badRequest().body("id must be null");
        }
        Organization saved = service.create(organization);
        String userId = principal.getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AbstractOAuth2Token token = (AbstractOAuth2Token) authentication.getCredentials();
        userService.addRole(userId, saved.id(), Role.OWNER, token.getTokenValue());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(Principal principal, @PathVariable("id") UUID id) {
        String userId = principal.getName();
        if(!userService.hasRoleIn(userId, id, Role.OWNER)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
