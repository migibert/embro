package com.migibert.embro.domain.service;

import com.migibert.embro.domain.model.Skill;
import com.migibert.embro.domain.port.SkillPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class SkillService {

    private final SkillPort port;

    public Skill save(Skill skill) {
        return this.port.save(skill);
    }

    public void delete(UUID skillId) {
        this.port.deleteById(skillId);
    }

    public Optional<Skill> findById(UUID id) {
        return this.port.findById(id);
    }

    public Iterable<Skill> findAll() {
        return this.port.findAll();
    }
}
