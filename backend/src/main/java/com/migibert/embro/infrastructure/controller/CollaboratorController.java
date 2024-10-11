package com.migibert.embro.infrastructure.controller;

import com.migibert.embro.domain.model.Collaborator;
import com.migibert.embro.domain.model.Role;
import com.migibert.embro.domain.service.CollaboratorService;
import com.migibert.embro.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/organizations/{organizationId}/collaborators")
@AllArgsConstructor
public class CollaboratorController {

    private final CollaboratorService service;
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity list(Principal principal, @PathVariable("organizationId") UUID organizationId) {
        String userId = principal.getName();
        if(!userService.hasAnyRole(userId, organizationId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Iterable<Collaborator> collaborators = service.findAll(organizationId);
        return ResponseEntity.ok(collaborators);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(Principal principal, @PathVariable("organizationId") UUID organizationId, @PathVariable("id") UUID id) {
        String userId = principal.getName();
        if(!userService.hasAnyRole(userId, organizationId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Collaborator> collaborator = service.findById(organizationId, id);
        if(collaborator.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(collaborator.get());
    }

    @PostMapping("/")
    public ResponseEntity create(Principal principal, @PathVariable("organizationId") UUID organizationId, @RequestBody Collaborator collaborator) {
        String userId = principal.getName();
        if(!userService.hasRoleIn(userId, organizationId, Role.OWNER, Role.EDITOR)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if(collaborator.id() != null) {
            return ResponseEntity.badRequest().body("id must be null");
        }
        Collaborator saved = service.create(organizationId, collaborator);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(Principal principal, @PathVariable("organizationId") UUID organizationId, @RequestBody Collaborator collaborator) {
        String userId = principal.getName();
        if(!userService.hasRoleIn(userId, organizationId, Role.OWNER, Role.EDITOR)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if(collaborator.id() == null) {
            return ResponseEntity.badRequest().body("id must not be null");
        }
        Collaborator updated = service.update(organizationId, collaborator);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(Principal principal, @PathVariable("organizationId") UUID organizationId, @PathVariable("id") UUID id) {
        String userId = principal.getName();
        if(!userService.hasRoleIn(userId, organizationId, Role.OWNER, Role.EDITOR)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        service.delete(organizationId, id);
        return ResponseEntity.noContent().build();
    }
}
