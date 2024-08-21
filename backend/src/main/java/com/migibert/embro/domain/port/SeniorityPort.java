package com.migibert.embro.domain.port;

import com.migibert.embro.domain.model.Seniority;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeniorityPort {
    Seniority save(UUID organizationId, Seniority seniority);

    void deleteById(UUID organizationId, UUID seniorityId);

    Optional<Seniority> findById(UUID organizationId, UUID seniorityId);

    List<Seniority> findAll(UUID organizationId);
}
