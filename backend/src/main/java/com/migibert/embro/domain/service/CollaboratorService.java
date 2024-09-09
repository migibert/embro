package com.migibert.embro.domain.service;

import com.migibert.embro.domain.model.Collaborator;
import com.migibert.embro.domain.port.CollaboratorPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CollaboratorService {
    private final CollaboratorPort port;

    public Collaborator create(UUID organizationId, Collaborator collaborator) {
        UUID id = UUID.randomUUID();
        return this.port.create(
            organizationId,
            new Collaborator(
                id,
                collaborator.email(),
                collaborator.firstname(),
                collaborator.lastname(),
                collaborator.role(),
                collaborator.birthDate(),
                collaborator.startDate(),
                collaborator.seniority(),
                collaborator.skills()
            )
        );
    }

    public Collaborator update(UUID organizationId, Collaborator collaborator) {
        return this.port.update(organizationId, collaborator);
    }

    public void delete(UUID organizationId, UUID collaboratorId) {
        this.port.deleteById(organizationId, collaboratorId);
    }

    public Optional<Collaborator> findById(UUID organizationId, UUID collaboratorId) {
        return this.port.findById(organizationId, collaboratorId);
    }

    public Set<Collaborator> findByTeam(UUID organizationId, UUID teamId) {
        return this.port.findByTeam(organizationId, teamId);
    }

    public Set<Collaborator> findByRole(UUID organizationId, String roleName) {
        return this.port.findByRole(organizationId, roleName);
    }

    public Set<Collaborator> findAll(UUID organizationId) {
        return this.port.findAll(organizationId);
    }
}
