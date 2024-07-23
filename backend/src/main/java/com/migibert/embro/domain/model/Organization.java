package com.migibert.embro.domain.model;

import java.util.UUID;


public record Organization(
        UUID id,
        String name) {
}