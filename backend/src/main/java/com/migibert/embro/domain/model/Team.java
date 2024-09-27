package com.migibert.embro.domain.model;

import java.util.UUID;

public record Team(
        UUID id,
        String name,
        String mission,
        String email,
        String instantMessage,
        String phone) {
}