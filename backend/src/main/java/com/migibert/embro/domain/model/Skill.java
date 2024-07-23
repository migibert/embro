package com.migibert.embro.domain.model;

import java.util.List;
import java.util.UUID;


public record Skill(
        UUID organizationId,
        String name,
        List<SkillCategory> categories) {
}
