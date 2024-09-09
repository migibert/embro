package com.migibert.embro.domain.port;

import java.util.List;
import java.util.UUID;

public interface UserPort {
    boolean isRelated(String userId, UUID organizationId);

    void relate(String userId, UUID organizationId);

    List<UUID> getRelations(String userId);
}
