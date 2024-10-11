package com.migibert.embro.infrastructure.controller;

import com.migibert.embro.domain.model.Role;
import com.migibert.embro.domain.model.Skill;
import com.migibert.embro.domain.service.SkillService;
import com.migibert.embro.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/organizations/{organizationId}/skills")
@AllArgsConstructor
public class SkillController {

    private final SkillService service;
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity list(Principal principal, @PathVariable("organizationId") UUID organizationId) {
        String userId = principal.getName();
        if(!userService.hasAnyRole(userId, organizationId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Iterable<Skill> skills = service.findAll(organizationId);
        return ResponseEntity.ok(skills);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(Principal principal, @PathVariable("organizationId") UUID organizationId, @PathVariable("id") UUID id) {
        String userId = principal.getName();
        if(!userService.hasAnyRole(userId, organizationId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Skill> skill = service.findById(organizationId, id);
        if(skill.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(skill.get());
    }

    @PostMapping("/")
    public ResponseEntity create(Principal principal, @PathVariable("organizationId") UUID organizationId, @RequestBody Skill skill) {
        String userId = principal.getName();
        if(!userService.hasRoleIn(userId, organizationId, Role.OWNER, Role.EDITOR)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if(skill.id() != null) {
            return ResponseEntity.badRequest().body("id must be null");
        }
        Skill saved = service.create(organizationId, skill);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
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
