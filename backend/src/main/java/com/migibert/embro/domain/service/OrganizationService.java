package com.migibert.embro.domain.service;

import com.migibert.embro.domain.model.Organization;
import com.migibert.embro.domain.port.OrganizationPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class OrganizationService {
    private final OrganizationPort port;

    public Organization create(Organization organization) {
        UUID id = UUID.randomUUID();
        return this.port.save(new Organization(id, organization.name()));
    }

    public Organization update(Organization organization) {
        return this.port.save(organization);
    }

    public void delete(UUID organizationId) {
        this.port.deleteById(organizationId);
    }

    public Optional<Organization> findById(UUID organizationId) {
        return this.port.findById(organizationId);
    }

    public Iterable<Organization> findAll() {
        return this.port.findAll();
    }
}
