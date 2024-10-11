package com.migibert.embro.infrastructure.controller;

import com.migibert.embro.domain.model.Collaborator;
import com.migibert.embro.domain.model.Member;
import com.migibert.embro.domain.model.Role;
import com.migibert.embro.domain.model.Team;
import com.migibert.embro.domain.service.CollaboratorService;
import com.migibert.embro.domain.service.TeamService;
import com.migibert.embro.domain.service.UserService;
import com.migibert.embro.infrastructure.controller.dto.MemberDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/organizations/{organizationId}/teams")
@AllArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final CollaboratorService collaboratorService;
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity list(Principal principal, @PathVariable("organizationId") UUID organizationId) {
        String userId = principal.getName();
        if(!userService.hasAnyRole(userId, organizationId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Iterable<Team> teams = teamService.findAll(organizationId);
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(Principal principal, @PathVariable("organizationId") UUID organizationId, @PathVariable("id") UUID id) {
        String userId = principal.getName();
        if(!userService.hasAnyRole(userId, organizationId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Team> team = teamService.findById(organizationId, id);
        if(team.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(team.get());
    }

    @PostMapping("/")
    public ResponseEntity create(Principal principal, @PathVariable("organizationId") UUID organizationId, @RequestBody Team team) {
        String userId = principal.getName();
        if(!userService.hasRoleIn(userId, organizationId, Role.OWNER, Role.EDITOR)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if(team.id() != null) {
            return ResponseEntity.badRequest().body("id must be null");
        }
        Team saved = teamService.create(organizationId, team);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(Principal principal, @PathVariable("organizationId") UUID organizationId, @RequestBody Team team) {
        String userId = principal.getName();
        if(!userService.hasRoleIn(userId, organizationId, Role.OWNER, Role.EDITOR)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if(team.id() == null) {
            return ResponseEntity.badRequest().body("id must not be null");
        }
        Team updated = teamService.update(organizationId, team);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(Principal principal, @PathVariable("organizationId") UUID organizationId, @PathVariable("id") UUID id) {
        String userId = principal.getName();
        if(!userService.hasRoleIn(userId, organizationId, Role.OWNER, Role.EDITOR)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        teamService.delete(organizationId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{teamId}/members/")
    public ResponseEntity listMembers(Principal principal, @PathVariable("organizationId") UUID organizationId, @PathVariable("teamId") UUID teamId) {
        String userId = principal.getName();
        if(!userService.hasAnyRole(userId, organizationId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Set<Member> members = teamService.listMembers(organizationId, teamId);
        return ResponseEntity.ok(members);
    }

    @PutMapping("/{teamId}/members/{memberId}")
    public ResponseEntity addMember(Principal principal, @PathVariable("organizationId") UUID organizationId, @PathVariable("teamId") UUID teamId, @PathVariable("memberId") UUID memberId, @RequestBody MemberDto dto) {
        String userId = principal.getName();
        if(!userService.hasRoleIn(userId, organizationId, Role.OWNER, Role.EDITOR)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Member added = teamService.addMember(organizationId, teamId, memberId, dto.keyPlayer());
        return ResponseEntity.ok(added);
    }

    @DeleteMapping("/{teamId}/members/{memberId}")
    public ResponseEntity removeMember(Principal principal, @PathVariable("organizationId") UUID organizationId, @PathVariable("teamId") UUID teamId, @PathVariable("memberId") UUID memberId) {
        String userId = principal.getName();
        if(!userService.hasRoleIn(userId, organizationId, Role.OWNER, Role.EDITOR)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        teamService.removeMember(organizationId, teamId, memberId);
        return ResponseEntity.noContent().build();
    }
}
