package com.migibert.embro.domain.port;

import com.migibert.embro.domain.model.Role;
import com.migibert.embro.domain.model.Skill;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RolePort {
    Role save(UUID organizationId, Role role);

    void deleteById(UUID organizationId, UUID roleId);

    Optional<Role> findById(UUID organizationId, UUID id);

    List<Role> findAll(UUID organizationId);
}
