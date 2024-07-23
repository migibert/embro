package com.migibert.embro.domain.port;

import com.migibert.embro.domain.model.Skill;

import java.util.List;
import java.util.UUID;

public interface SkillPort {
    Skill save(Skill skill);

    void delete(UUID skillId);

    Skill findById(UUID id);

    List<Skill> findAll();
}
