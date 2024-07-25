package com.migibert.embro.domain.port;

import com.migibert.embro.domain.model.Skill;

import java.util.Optional;
import java.util.UUID;

public interface SkillPort {
    Skill save(Skill skill);

    void deleteById(UUID skillId);

    Optional<Skill> findById(UUID id);

    Iterable<Skill> findAll();
}
