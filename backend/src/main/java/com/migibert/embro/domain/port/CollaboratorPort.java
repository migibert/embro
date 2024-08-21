package com.migibert.embro.domain.port;

import com.migibert.embro.domain.model.Collaborator;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CollaboratorPort {
    Collaborator create(UUID organizationId, Collaborator collaborator);

    Collaborator update(UUID organizationId, Collaborator collaborator);
    void deleteById(UUID organizationId, UUID collaboratorId);

    Optional<Collaborator> findById(UUID organizationId, UUID collaboratorId);

    Set<Collaborator> findAll(UUID organizationId);

    Set<Collaborator> findByTeam(UUID organizationId, UUID teamID);

    Set<Collaborator> findBySkill(UUID organizationId, UUID skillId);

    Set<Collaborator> findByName(UUID organizationId, String name);
}
