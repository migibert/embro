package com.migibert.embro.domain.service;

import com.migibert.embro.domain.model.Invitation;
import com.migibert.embro.domain.model.Role;
import com.migibert.embro.domain.model.User;
import com.migibert.embro.domain.port.UserPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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

    public Map<UUID, Role> getRolesByOrganizationForUser(String userId) {
        return repository.getRolesByOrganizationIdForUser(userId);
    }

    public boolean hasAnyRole(String userId, UUID organizationId) {
        return repository.hasRoleIn(userId, organizationId, Role.values());
    }

    public boolean hasRoleIn(String userId, UUID organizationId, Role... roles) {
        return repository.hasRoleIn(userId, organizationId, roles);
    }

    public Invitation createInvitation(String invitedBy, String emailToInvite, UUID organizationId, Role role) {
        UUID token = UUID.randomUUID();
        Invitation toCreate = new Invitation(token, invitedBy, emailToInvite, organizationId, role);
        repository.createInvitation(toCreate);
        return toCreate;
    }

    public boolean deleteInvitation(String userId, UUID id) {
        Optional<Invitation> found = repository.findInvitationById(id);
        if(found.isEmpty()) {
            throw new IllegalArgumentException("Invitation does not exist");
        }
        Invitation existing = found.get();
        UUID organizationId = existing.organizationId();
        if(!hasRoleIn(userId, organizationId, Role.OWNER)) {
            return false;
        }
        repository.deleteInvitation(id);
        return true;
    }

    public void deleteInvitationsByOrganizationId(UUID organizationId) {
        repository.deleteInvitationsByOrganizationId(organizationId);
    }

    public Optional<Invitation> acceptInvitation(String userId, UUID id, String accessToken) {
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

    public List<User> getUsersByOrganizationId(UUID organizationId) {
        return repository.getUsersByOrganizationId(organizationId);
    }

    public List<Invitation> getInvitationsByOrganizationId(UUID organizationId) {
        return repository.getInvitationsByOrganizationId(organizationId);
    }

    public void updateUser(UUID organizationId, User user) {
        Optional<User> stored = repository.getUser(organizationId, user.email());
        if(stored.isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }
        repository.updateUser(organizationId, user);
    }

    public void removeUserFromOrganizationById(String userId, UUID organizationId) {
        Map<Role, Integer> roleCount = repository.userCountPerRole(organizationId);
        roleCount.forEach((role, count) -> log.info("{} : {}", role, count));
        Integer ownerCount = roleCount.get(Role.OWNER);
        if(ownerCount == null) {
            throw new NullPointerException("There should be at least 1 owner for an organization");
        }
        if(ownerCount.equals(1)) {
            throw new IllegalArgumentException("Last Owner cannot be removed");
        }
        repository.deleteUserFromOrganization(userId, organizationId);
    }

    public void removeUserFromOrganizationByEmail(String email, UUID organizationId) {
        String userId = repository.findUserIdByEmail(email, organizationId);
        Map<Role, Integer> roleCount = repository.userCountPerRole(organizationId);
        if(roleCount.get(Role.OWNER) == 1) {
            if(repository.hasRoleIn(userId, organizationId, Role.OWNER)) {
                throw new IllegalArgumentException("Last Owner cannot be removed");
            }
        }
        repository.deleteUserFromOrganization(userId, organizationId);
    }

    public String getEmailFromAccessToken(String accessToken) {
        return repository.findUserEmail(accessToken);
    }
}
