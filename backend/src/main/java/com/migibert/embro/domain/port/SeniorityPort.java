package com.migibert.embro.domain.port;

import com.migibert.embro.domain.model.Seniority;

import java.util.List;
import java.util.UUID;

public interface SeniorityPort {
    Seniority save(Seniority seniority);

    void delete(UUID seniorityId);

    Seniority findById(UUID seniorityId);

    List<Seniority> findAll();
}
