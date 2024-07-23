package com.migibert.embro.domain.service;

import com.migibert.embro.domain.model.Skill;
import com.migibert.embro.domain.port.SkillPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class SkillService {

    private final SkillPort port;

    public Skill save(Skill skill) {
        return this.port.save(skill);
    }

    public void delete(UUID skillId) {
        this.port.delete(skillId);
    }

    public Skill findById(UUID id) {
        return this.port.findById(id);
    }

    public List<Skill> findAll() {
        return this.port.findAll();
    }
}
