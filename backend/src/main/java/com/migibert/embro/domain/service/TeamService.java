package com.migibert.embro.domain.service;

import com.migibert.embro.domain.model.Team;
import com.migibert.embro.domain.port.TeamPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class TeamService {
    private final TeamPort port;

    public Team create(UUID organizationId, Team team) {
        UUID id = UUID.randomUUID();
        return this.port.create(organizationId, new Team(id, team.name()));
    }
    public Team update(UUID organizationId, Team team) {
        return this.port.update(organizationId, team);
    }

    public void delete(UUID organizationId, UUID teamId) {
        this.port.deleteById(organizationId, teamId);
    }

    public Optional<Team> findById(UUID organizationId, UUID teamId) {
        return this.port.findById(organizationId, teamId);
    }

    public List<Team> findAll(UUID organizationId) {
        return this.port.findAll(organizationId);
    }
}
