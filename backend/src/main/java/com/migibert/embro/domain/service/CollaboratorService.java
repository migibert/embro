package com.migibert.embro.domain.service;

import com.migibert.embro.domain.model.Collaborator;
import com.migibert.embro.domain.port.CollaboratorPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CollaboratorService {
    private final CollaboratorPort port;

    public Collaborator save(Collaborator collaborator) {
        return this.port.save(collaborator);
    }

    public void delete(UUID collaboratorId) {
        this.port.deleteById(collaboratorId);
    }

    public Optional<Collaborator> findById(UUID collaboratorId) {
        return this.port.findById(collaboratorId);
    }

    public Iterable<Collaborator> findAll() {
        return this.port.findAll();
    }
}
