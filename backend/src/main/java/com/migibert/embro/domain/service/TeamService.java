package com.migibert.embro.domain.service;

import com.migibert.embro.domain.model.Team;
import com.migibert.embro.domain.port.TeamPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class TeamService {
    private final TeamPort port;

    public Team save(Team team) {
        return this.port.save(team);
    }

    public void delete(UUID teamId) {
        this.port.deleteById(teamId);
    }

    public Optional<Team> findById(UUID teamId) {
        return this.port.findById(teamId);
    }

    public Iterable<Team> findAll() {
        return this.port.findAll();
    }
}
