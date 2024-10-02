package com.migibert.embro.domain.service;

import com.migibert.embro.domain.model.Position;
import com.migibert.embro.domain.port.PositionPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@AllArgsConstructor
@Service
public class PositionService {
    private final PositionPort port;

    public Position update(UUID organizationId, Position position) {
        return this.port.save(organizationId, position);
    }

    public void delete(UUID organizationId, UUID id) {
        this.port.deleteById(organizationId, id);
    }

    public Optional<Position> findById(UUID organizationId, UUID id) {
        return this.port.findById(organizationId, id);
    }

    public List<Position> findAll(UUID organizationId) {
        return this.port.findAll(organizationId);
    }

    public Position create(UUID organizationId, Position position) {
        UUID id = UUID.randomUUID();
        return this.port.save(organizationId, new Position(id, position.name()));
    }
}
