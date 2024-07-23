package com.migibert.embro.domain.service;

import com.migibert.embro.domain.model.Team;
import com.migibert.embro.domain.port.TeamPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class TeamService {
    private final TeamPort port;

    public Team save(Team team) {
        return this.port.save(team);
    }

    public void delete(UUID teamId) {
        this.port.delete(teamId);
    }

    public Team findById(UUID teamId) {
        return this.port.findById(teamId);
    }

    public List<Team> findAll() {
        return this.port.findAll();
    }
}
