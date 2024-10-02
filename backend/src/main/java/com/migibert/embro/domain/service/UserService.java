package com.migibert.embro.domain.service;

import com.migibert.embro.domain.model.Invitation;
import com.migibert.embro.domain.model.Role;
import com.migibert.embro.domain.model.User;
import com.migibert.embro.domain.port.UserPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@Service
public class UserService {

    private UserPort repository;

    public void addRole(String userId, UUID organizationId, Role role, String accessToken) {
        String email = repository.findUserEmail(accessToken);
        repository.addRole(userId, email, organizationId, role);
    }

    public boolean hasAnyRole(String userId, UUID organizationId) {
        return repository.hasRoleIn(userId, organizationId, Role.values());
    }

    public boolean hasRoleIn(String userId, UUID organizationId, Role... roles) {
        return repository.hasRoleIn(userId, organizationId, roles);
    }

    public Invitation invite(String invitedBy, String emailToInvite, UUID organizationId, Role role) {
        UUID token = UUID.randomUUID();
        Invitation toCreate = new Invitation(token, invitedBy, emailToInvite, organizationId, role);
        repository.createInvitation(toCreate);
        return toCreate;
    }

    public Optional<Invitation> consumeInvitation(String userId, UUID id, String accessToken) {
        Optional<Invitation> found = repository.findInvitationById(id);
        if(found.isEmpty()) {
            return Optional.empty();
        }
        Invitation invitation = found.get();
        String userEmail = repository.findUserEmail(accessToken);
        if(!userEmail.equals(invitation.email())) {
            throw new IllegalArgumentException("Email do not match");
        }
        repository.addRole(userId, userEmail, invitation.organizationId(), invitation.role());
        repository.deleteInvitation(id);
        return Optional.of(invitation);
    }

    public List<UUID> getOrganizations(String userId) {
        return repository.getOrganizationIds(userId);
    }

    public List<User> getUsersByOrganizationId(UUID id) {
        return repository.getUsersByOrganizationId(id);
    }

    public void updateUser(UUID organizationId, User user) {
        repository.updateUser(organizationId, user);
    }
}
