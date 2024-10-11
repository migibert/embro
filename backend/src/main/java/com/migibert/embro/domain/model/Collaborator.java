package com.migibert.embro.domain.model;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record Collaborator(
        UUID id,
        String email,
        String firstname,
        String lastname,
        String position,
        LocalDate birthDate,
        LocalDate startDate,
        String seniority,
        Set<SkillLevel> skills) {
}
