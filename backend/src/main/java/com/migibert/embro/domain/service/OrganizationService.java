package com.migibert.embro.domain.service;

import com.migibert.embro.domain.model.Organization;
import com.migibert.embro.domain.port.OrganizationPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class OrganizationService {
    private final OrganizationPort port;

    public Organization save(Organization organization) {
        return this.port.save(organization);
    }

    public void delete(UUID organizationId) {
        this.port.delete(organizationId);
    }

    public Organization findById(UUID organizationId) {
        return this.port.findById(organizationId);
    }

    public List<Organization> findAll() {
        return this.port.findAll();
    }
}
