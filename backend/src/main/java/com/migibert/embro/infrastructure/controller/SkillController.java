package com.migibert.embro.infrastructure.controller;

import com.migibert.embro.domain.model.Skill;
import com.migibert.embro.domain.service.SkillService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/organizations/{organizationId}/skills")
@AllArgsConstructor
public class SkillController {

    private final SkillService service;

    @GetMapping("/")
    public ResponseEntity list(@PathVariable("organizationId") UUID organizationId) {
        Iterable<Skill> skills = service.findAll(organizationId);
        return ResponseEntity.ok(skills);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("organizationId") UUID organizationId, @PathVariable("id") UUID id) {
        Optional<Skill> skill = service.findById(organizationId, id);
        if(skill.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(skill.get());
    }

    @PostMapping("/")
    public ResponseEntity create(@PathVariable("organizationId") UUID organizationId, @RequestBody Skill skill) {
        if(skill.id() != null) {
            return ResponseEntity.badRequest().body("id must be null");
        }
        Skill saved = service.create(organizationId, skill);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("organizationId") UUID organizationId, @PathVariable("id") UUID id) {
        service.delete(organizationId, id);
        return ResponseEntity.noContent().build();
    }
}
