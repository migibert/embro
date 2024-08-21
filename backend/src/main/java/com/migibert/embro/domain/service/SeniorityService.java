package com.migibert.embro.domain.service;

import com.migibert.embro.domain.model.Seniority;
import com.migibert.embro.domain.port.SeniorityPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@AllArgsConstructor
@Service
public class SeniorityService {
    private final SeniorityPort port;

    public Seniority update(UUID organizationId, Seniority seniority) {
        return this.port.save(organizationId, seniority);
    }

    public void delete(UUID organizationId, UUID seniorityId) {
        this.port.deleteById(organizationId, seniorityId);
    }

    public Optional<Seniority> findById(UUID organizationId, UUID seniorityId) {
        return this.port.findById(organizationId, seniorityId);
    }

    public List<Seniority> findAll(UUID organizationId) {
        return this.port.findAll(organizationId);
    }

    public Seniority create(UUID organizationId, Seniority seniority) {
        UUID id = UUID.randomUUID();
        return this.port.save(organizationId, new Seniority(id, seniority.name()));
    }
}
