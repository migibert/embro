package com.migibert.embro.domain.model;

import java.util.UUID;

public record SkillLevel(
        Skill skill,
        int proficiency) {
}
