package com.migibert.embro.infrastructure.controller;

import com.migibert.embro.domain.model.Collaborator;
import com.migibert.embro.domain.model.Team;
import com.migibert.embro.domain.service.CollaboratorService;
import com.migibert.embro.domain.service.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/organizations/{organizationId}/teams")
@AllArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final CollaboratorService collaboratorService;

    @GetMapping("/")
    public ResponseEntity list(@PathVariable("organizationId") UUID organizationId) {
        Iterable<Team> teams = teamService.findAll(organizationId);
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("organizationId") UUID organizationId, @PathVariable("id") UUID id) {
        Optional<Team> team = teamService.findById(organizationId, id);
        if(team.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(team.get());
    }

    @PostMapping("/")
    public ResponseEntity create(@PathVariable("organizationId") UUID organizationId, @RequestBody Team team) {
        if(team.id() != null) {
            return ResponseEntity.badRequest().body("id must be null");
        }
        Team saved = teamService.create(organizationId, team);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("organizationId") UUID organizationId, @RequestBody Team team) {
        if(team.id() == null) {
            return ResponseEntity.badRequest().body("id must not be null");
        }
        Team updated = teamService.update(organizationId, team);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("organizationId") UUID organizationId, @PathVariable("id") UUID id) {
        teamService.delete(organizationId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{teamId}/members/")
    public ResponseEntity listMembers(@PathVariable("organizationId") UUID organizationId, @PathVariable("teamId") UUID id) {
        Set<Collaborator> members = collaboratorService.findByTeam(organizationId, id);
        return ResponseEntity.ok(members);
    }

    @PutMapping("/{teamId}/members/{memberId}")
    public ResponseEntity addMember(@PathVariable("organizationId") UUID organizationId, @PathVariable("teamId") UUID teamId, @PathVariable("memberId") UUID memberId) {
        teamService.addMember(organizationId, teamId, memberId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{teamId}/members/{memberId}")
    public ResponseEntity removeMember(@PathVariable("organizationId") UUID organizationId, @PathVariable("teamId") UUID teamId, @PathVariable("memberId") UUID memberId) {
        teamService.removeMember(organizationId, teamId, memberId);
        return ResponseEntity.noContent().build();
    }
}
