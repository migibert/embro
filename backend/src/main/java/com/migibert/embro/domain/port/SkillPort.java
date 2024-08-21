package com.migibert.embro.domain.port;

import com.migibert.embro.domain.model.Skill;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SkillPort {
    Skill save(UUID organizationId, Skill skill);

    void deleteById(UUID organizationId, UUID skillId);

    Optional<Skill> findById(UUID organizationId, UUID id);

    List<Skill> findAll(UUID organizationId);
}
