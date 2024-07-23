package com.migibert.embro.domain.port;

import com.migibert.embro.domain.model.Team;

import java.util.List;
import java.util.UUID;

public interface TeamPort {
    Team save(Team team);

    void delete(UUID teamId);

    Team findById(UUID teamId);

    List<Team> findAll();
}
