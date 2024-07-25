package com.migibert.embro.infrastructure.controller;

import com.migibert.embro.domain.model.Organization;
import com.migibert.embro.domain.service.OrganizationService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/organizations")
@AllArgsConstructor
public class OrganizationController {

    private final OrganizationService service;

    @RequestMapping("/")
    public ResponseEntity list() {
        Iterable<Organization> organizations = service.findAll();
        return ResponseEntity.ok(organizations);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") UUID id) {
        Optional<Organization> organization = service.findById(id);
        if(organization.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(organization.get());
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody Organization organization) {
        if(organization.id() != null) {
            return ResponseEntity.badRequest().body("id must be null");
        }
        Organization saved = service.create(organization);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
