package com.migibert.embro.domain.port;

import com.migibert.embro.domain.model.Position;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PositionPort {
    Position save(UUID organizationId, Position position);

    void deleteById(UUID organizationId, UUID id);

    Optional<Position> findById(UUID organizationId, UUID id);

    List<Position> findAll(UUID organizationId);
}
