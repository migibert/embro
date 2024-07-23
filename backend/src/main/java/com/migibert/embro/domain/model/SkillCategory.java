package com.migibert.embro.domain.model;

import java.util.UUID;


public record SkillCategory(
        UUID organizationId,
        UUID id,
        String name) {
}
