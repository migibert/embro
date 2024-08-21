package com.migibert.embro.domain.port;

import com.migibert.embro.domain.model.Team;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface TeamPort {
    Team create(UUID organizationId, Team team);

    Team update(UUID organizationId, Team team);

    void deleteById(UUID organizationId, UUID teamId);

    Optional<Team> findById(UUID organizationId, UUID teamId);

    Set<Team> findAll(UUID organizationId);

    void addMember(UUID organizationId, UUID teamId, UUID memberId);

    void removeMember(UUID organizationId, UUID teamId, UUID memberId);
}
