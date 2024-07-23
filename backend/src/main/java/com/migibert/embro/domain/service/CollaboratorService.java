package com.migibert.embro.domain.service;

import com.migibert.embro.domain.model.Collaborator;
import com.migibert.embro.domain.port.CollaboratorPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class CollaboratorService {
    private final CollaboratorPort port;

    public Collaborator save(Collaborator collaborator) {
        return this.port.save(collaborator);
    }

    public void delete(UUID collaboratorId) {
        this.port.delete(collaboratorId);
    }

    public Collaborator findById(UUID collaboratorId) {
        return this.port.findById(collaboratorId);
    }

    public List<Collaborator> findAll() {
        return this.port.findAll();
    }
}
