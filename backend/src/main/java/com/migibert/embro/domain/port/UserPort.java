package com.migibert.embro.domain.port;

import com.migibert.embro.domain.model.Invitation;
import com.migibert.embro.domain.model.Role;
import com.migibert.embro.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserPort {

    void addRole(String userId, String email, UUID organizationId, Role role);

    List<UUID> getOrganizationIds(String userId);

    boolean hasRoleIn(String userId, UUID organizationId, Role[] roles);

    void createInvitation(Invitation invitation);

    void deleteInvitation(UUID id);

    Optional<Invitation> findInvitationById(UUID id);

    String findUserEmail(String accessToken);

    List<User> getUsersByOrganizationId(UUID id);

    void updateUser(UUID organizationId, User user);
}
