package com.migibert.embro.domain.port;

import com.migibert.embro.domain.model.Collaborator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CollaboratorPort {
    Collaborator create(UUID organizationId, Collaborator collaborator);

    Collaborator update(UUID organizationId, Collaborator collaborator);
    void deleteById(UUID organizationId, UUID collaboratorId);

    Optional<Collaborator> findById(UUID organizationId, UUID collaboratorId);

    List<Collaborator> findAll(UUID organizationId);

    List<Collaborator> findByTeam(UUID organizationId, UUID teamID);

    List<Collaborator> findBySkill(UUID organizationId, UUID skillId);

    List<Collaborator> findByName(UUID organizationId, String name);
}
