package com.migibert.embro.domain.port;

import com.migibert.embro.domain.model.Organization;

import java.util.List;
import java.util.UUID;

public interface OrganizationPort {
    Organization save(Organization organization);

    void delete(UUID organizationId);

    Organization findById(UUID organizationId);

    List<Organization> findAll();
}
