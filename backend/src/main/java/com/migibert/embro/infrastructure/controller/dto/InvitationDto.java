package com.migibert.embro.infrastructure.controller.dto;

import java.util.UUID;

public record InvitationDto(
        String email,
        UUID organizationId,
        String role
) {}