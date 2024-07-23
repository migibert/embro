package com.migibert.embro.domain.model;

import java.util.UUID;

public record Seniority(
        UUID organizationId,
        String name) {
}
