package com.migibert.embro.domain.service;

import com.migibert.embro.domain.port.UserPort;
import com.migibert.embro.infrastructure.persistence.adapter.UserAdapter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService {

    private UserPort repository;

    public boolean isAllowed(String userId, UUID organizationId) {
        return repository.isRelated(userId, organizationId);
    }

    public void allow(String userId, UUID organizationId) {
        repository.relate(userId, organizationId);
    }

    public List<UUID> getOrganizations(String userId) {
        return repository.getRelations(userId);
    }

}
