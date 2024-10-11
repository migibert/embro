package com.migibert.embro.domain.port;

import com.migibert.embro.domain.model.Organization;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface OrganizationPort {
    Organization save(Organization organization);

    void deleteById(UUID organizationId);

    Optional<Organization> findById(UUID organizationId);

    List<Organization> findAll();

    Set<Organization> findByIds(Set<UUID> ids);
}
