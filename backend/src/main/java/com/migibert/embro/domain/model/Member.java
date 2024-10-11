package com.migibert.embro.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public record Member(
        UUID collaboratorId,
        String firstname,
        String lastname,
        String email,
        String position,
        String seniority,
        LocalDate startDate,
        boolean keyPlayer) {
}
