package com.migibert.embro.infrastructure.controller;

import com.migibert.embro.domain.model.Seniority;
import com.migibert.embro.domain.service.SeniorityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/organizations/{organizationId}/seniorities")
@AllArgsConstructor
public class SeniorityController {

    private final SeniorityService service;

    @RequestMapping("/")
    public ResponseEntity list(@PathVariable("organizationId") UUID organizationId) {
        Iterable<Seniority> seniorities = service.findAll(organizationId);
        return ResponseEntity.ok(seniorities);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("organizationId") UUID organizationId, @PathVariable("id") UUID id) {
        Optional<Seniority> seniority = service.findById(organizationId, id);
        if(seniority.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(seniority.get());
    }

    @PostMapping("/")
    public ResponseEntity create(@PathVariable("organizationId") UUID organizationId, @RequestBody Seniority seniority) {
        if(seniority.id() != null) {
            return ResponseEntity.badRequest().body("id must be null");
        }
        Seniority saved = service.create(organizationId, seniority);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("organizationId") UUID organizationId, @PathVariable("id") UUID id) {
        service.delete(organizationId, id);
        return ResponseEntity.noContent().build();
    }
}
