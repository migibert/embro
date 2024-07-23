package com.migibert.embro.domain.model;

import java.util.UUID;

public record SkillLevel(
        UUID organizationId,
        Skill skill,
        int proficiency) {
}
