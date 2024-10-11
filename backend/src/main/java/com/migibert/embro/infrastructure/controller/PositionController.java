package com.migibert.embro.infrastructure.controller;

import com.migibert.embro.domain.model.Position;
import com.migibert.embro.domain.model.Role;
import com.migibert.embro.domain.service.PositionService;
import com.migibert.embro.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/organizations/{organizationId}/positions")
@AllArgsConstructor
public class PositionController {

    private final PositionService service;
    private final UserService userService;

    @RequestMapping("/")
    public ResponseEntity list(Principal principal, @PathVariable("organizationId") UUID organizationId) {
        String userId = principal.getName();
        if (!userService.hasAnyRole(userId, organizationId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Iterable<Position> positions = service.findAll(organizationId);
        return ResponseEntity.ok(positions);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(Principal principal, @PathVariable("organizationId") UUID organizationId, @PathVariable("id") UUID id) {
        String userId = principal.getName();
        if (!userService.hasAnyRole(userId, organizationId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Position> position = service.findById(organizationId, id);
        if (position.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(position.get());
    }

    @PostMapping("/")
    public ResponseEntity create(Principal principal, @PathVariable("organizationId") UUID organizationId, @RequestBody Position position) {
        String userId = principal.getName();
        if (!userService.hasRoleIn(userId, organizationId, Role.OWNER, Role.EDITOR)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (position.id() != null) {
            return ResponseEntity.badRequest().body("id must be null");
        }
        Position saved = service.create(organizationId, position);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(Principal principal, @PathVariable("organizationId") UUID organizationId, @PathVariable("id") UUID id) {
        String userId = principal.getName();
        if (!userService.hasRoleIn(userId, organizationId, Role.OWNER, Role.EDITOR)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        service.delete(organizationId, id);
        return ResponseEntity.noContent().build();
    }
}