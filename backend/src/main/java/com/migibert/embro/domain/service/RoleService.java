package com.migibert.embro.domain.service;

import com.migibert.embro.domain.model.Role;
import com.migibert.embro.domain.port.RolePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@AllArgsConstructor
@Service
public class RoleService {
    private final RolePort port;

    public Role update(UUID organizationId, Role role) {
        return this.port.save(organizationId, role);
    }

    public void delete(UUID organizationId, UUID roleId) {
        this.port.deleteById(organizationId, roleId);
    }

    public Optional<Role> findById(UUID organizationId, UUID roleId) {
        return this.port.findById(organizationId, roleId);
    }

    public List<Role> findAll(UUID organizationId) {
        return this.port.findAll(organizationId);
    }

    public Role create(UUID organizationId, Role role) {
        UUID id = UUID.randomUUID();
        return this.port.save(organizationId, new Role(id, role.name()));
    }
}
