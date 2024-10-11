package com.migibert.embro.infrastructure.controller.dto;

import com.migibert.embro.domain.model.Organization;
import com.migibert.embro.domain.model.Role;

public record UserInfo(
    Organization organization,
    Role role)
{}