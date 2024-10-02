package com.migibert.embro.domain.model;

import java.util.UUID;

public record Invitation(
        UUID id,
        String invitedBy,
        String email,
        UUID organizationId,
        Role role
) {}
