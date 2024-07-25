package com.migibert.embro.domain.port;

import com.migibert.embro.domain.model.Team;

import java.util.Optional;
import java.util.UUID;

public interface TeamPort {
    Team save(Team team);

    void deleteById(UUID teamId);

    Optional<Team> findById(UUID teamId);

    Iterable<Team> findAll();
}
