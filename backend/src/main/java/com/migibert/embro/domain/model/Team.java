package com.migibert.embro.domain.model;

import java.util.List;
import java.util.UUID;

public record Team(
        UUID organizationId,
        UUID id,
        String name,
        List<Collaborator> members) {
}
