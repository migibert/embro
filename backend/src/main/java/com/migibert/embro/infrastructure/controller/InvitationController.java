package com.migibert.embro.infrastructure.controller;

import com.migibert.embro.domain.model.Invitation;
import com.migibert.embro.domain.model.Role;
import com.migibert.embro.domain.service.UserService;
import com.migibert.embro.infrastructure.controller.dto.InvitationDto;
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
@RequestMapping("/invitations")
@AllArgsConstructor
public class InvitationController {

    private UserService service;

    @GetMapping
    public ResponseEntity list(Principal principal, @RequestParam("organizationId") UUID organizationId) {
        String userId = principal.getName();
        if(!service.hasAnyRole(userId, organizationId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Invitation> result = service.getInvitationsByOrganizationId(organizationId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(Principal principal, @PathVariable("id") UUID id) {
        String userId = principal.getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AbstractOAuth2Token token = (AbstractOAuth2Token) authentication.getCredentials();
        Optional<Invitation> result = service.acceptInvitation(userId, id, token.getTokenValue());
        if(result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(Principal principal, @PathVariable("id") UUID id) {
        String userId = principal.getName();
        boolean result = service.deleteInvitation(userId, id);
        if(!result) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/")
    public ResponseEntity post(Principal principal, @RequestBody InvitationDto dto) {
        String userId = principal.getName();
        if(!service.hasRoleIn(userId, dto.organizationId(), Role.OWNER)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Invitation invitation = service.createInvitation(userId, dto.email(), dto.organizationId(), Role.valueOf(dto.role()));
        return ResponseEntity.status(HttpStatus.CREATED).body(invitation);
    }
}
