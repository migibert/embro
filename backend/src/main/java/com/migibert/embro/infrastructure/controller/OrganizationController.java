package com.migibert.embro.infrastructure.controller;

import com.migibert.embro.domain.model.Organization;
import com.migibert.embro.domain.model.Role;
import com.migibert.embro.domain.model.User;
import com.migibert.embro.domain.service.OrganizationService;
import com.migibert.embro.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/organizations")
@AllArgsConstructor
public class OrganizationController {
    private final UserService userService;
    private final OrganizationService service;

    @GetMapping("/")
    public ResponseEntity list(Principal principal) {
        String userId = principal.getName();
        Map<UUID, Role> rolesByOrganization = userService.getRolesByOrganizationForUser(userId);
        Set<Organization> result = service.findByIds(rolesByOrganization.keySet());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(Principal principal, @PathVariable("id") UUID id) {
        String userId = principal.getName();
        if(!userService.hasAnyRole(userId, id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Organization> organization = service.findById(id);
        if(organization.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(organization.get());
    }

    @GetMapping("/{organizationId}/users/")
    public ResponseEntity getUsers(Principal principal, @PathVariable("organizationId") UUID organizationId) {
        String userId = principal.getName();
        if(!userService.hasAnyRole(userId, organizationId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Organization> organization = service.findById(organizationId);
        if(organization.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<User> result = userService.getUsersByOrganizationId(organizationId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{organizationId}/users/{email}")
    public ResponseEntity updateUser(Principal principal, @PathVariable("organizationId") UUID organizationId, @PathVariable("email") String email, @RequestBody User user) {
        String principalUserId = principal.getName();
        if(!userService.hasRoleIn(principalUserId, organizationId, Role.OWNER)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if(!email.equals(user.email())) {
            return ResponseEntity.badRequest().build();
        }
        userService.updateUser(organizationId, user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{organizationId}/users/{email}")
    public ResponseEntity deleteUser(Principal principal, @PathVariable("organizationId") UUID organizationId, @PathVariable("email") String email) {
        String principalUserId = principal.getName();

        // Remove a user other than self
        if(userService.hasRoleIn(principalUserId, organizationId, Role.OWNER)) {
            userService.removeUserFromOrganizationByEmail(email, organizationId);
            return ResponseEntity.noContent().build();
        }

        // Leave the organization
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AbstractOAuth2Token token = (AbstractOAuth2Token) authentication.getCredentials();
        String principalEmail = userService.getEmailFromAccessToken(token.getTokenValue());
        if(email.equals(principalEmail)) {
            userService.removeUserFromOrganizationByEmail(email, organizationId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/")
    public ResponseEntity create(Principal principal, @RequestBody Organization organization) {
        if(organization.id() != null) {
            return ResponseEntity.badRequest().body("id must be null");
        }
        String userId = principal.getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AbstractOAuth2Token token = (AbstractOAuth2Token) authentication.getCredentials();
        Organization saved = service.create(organization);
        userService.addRole(userId, saved.id(), Role.OWNER, token.getTokenValue());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(Principal principal, @PathVariable("id") UUID id) {
        String userId = principal.getName();
        if(!userService.hasRoleIn(userId, id, Role.OWNER)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        userService.deleteInvitationsByOrganizationId(id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
