package com.migibert.embro.domain.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record Collaborator(
        UUID organizationId,
        UUID id,
        String email,
        String firstname,
        String lastname,
        String role,
        Date birthDate,
        Date startDate,
        Seniority seniority,
        List<SkillLevel> skills) {
}
