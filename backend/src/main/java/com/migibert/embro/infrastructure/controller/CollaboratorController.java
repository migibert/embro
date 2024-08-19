package com.migibert.embro.infrastructure.controller;

import com.migibert.embro.domain.model.Collaborator;
import com.migibert.embro.domain.service.CollaboratorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/organizations/{organizationId}/collaborators")
@AllArgsConstructor
public class CollaboratorController {

    private final CollaboratorService service;

    @GetMapping("/")
    public ResponseEntity list(@PathVariable("organizationId") UUID organizationId) {
        Iterable<Collaborator> collaborators = service.findAll(organizationId);
        return ResponseEntity.ok(collaborators);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("organizationId") UUID organizationId, @PathVariable("id") UUID id) {
        Optional<Collaborator> collaborator = service.findById(organizationId, id);
        if(collaborator.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(collaborator.get());
    }

    @PostMapping("/")
    public ResponseEntity create(@PathVariable("organizationId") UUID organizationId, @RequestBody Collaborator collaborator) {
        if(collaborator.id() != null) {
            return ResponseEntity.badRequest().body("id must be null");
        }
        Collaborator saved = service.create(organizationId, collaborator);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("organizationId") UUID organizationId, @RequestBody Collaborator collaborator) {
        if(collaborator.id() == null) {
            return ResponseEntity.badRequest().body("id must not be null");
        }
        Collaborator updated = service.update(organizationId, collaborator);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("organizationId") UUID organizationId, @PathVariable("id") UUID id) {
        service.delete(organizationId, id);
        return ResponseEntity.noContent().build();
    }
}
