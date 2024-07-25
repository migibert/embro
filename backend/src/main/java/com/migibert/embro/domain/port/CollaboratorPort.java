package com.migibert.embro.domain.port;

import com.migibert.embro.domain.model.Collaborator;

import java.util.Optional;
import java.util.UUID;

public interface CollaboratorPort {
    Collaborator save(Collaborator collaborator);

    void deleteById(UUID collaboratorId);

    Optional<Collaborator> findById(UUID collaboratorId);

    Iterable<Collaborator> findAll();
}
