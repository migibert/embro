package com.migibert.embro.domain.model;

import java.util.UUID;


public record Skill(
        UUID organizationId,
        UUID id,
        String name) {
}
