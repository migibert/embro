package com.migibert.embro.domain.port;

import com.migibert.embro.domain.model.Collaborator;

import java.util.List;
import java.util.UUID;

public interface CollaboratorPort {
    Collaborator save(Collaborator collaborator);

    void delete(UUID collaboratorId);

    Collaborator findById(UUID collaboratorId);

    List<Collaborator> findAll();
}
