package com.migibert.embro.domain.port;

import com.migibert.embro.domain.model.Organization;

import java.util.Optional;
import java.util.UUID;

public interface OrganizationPort {
    Organization save(Organization organization);

    void deleteById(UUID organizationId);

    Optional<Organization> findById(UUID organizationId);

    Iterable<Organization> findAll();
}
