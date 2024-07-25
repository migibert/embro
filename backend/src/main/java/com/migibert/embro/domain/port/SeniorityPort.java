package com.migibert.embro.domain.port;

import com.migibert.embro.domain.model.Seniority;

import java.util.Optional;
import java.util.UUID;

public interface SeniorityPort {
    Seniority save(Seniority seniority);

    void deleteById(UUID seniorityId);

    Optional<Seniority> findById(UUID seniorityId);

    Iterable<Seniority> findAll();
}
