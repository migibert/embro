package com.migibert.embro.domain.service;

import com.migibert.embro.domain.model.Skill;
import com.migibert.embro.domain.port.SkillPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class SkillService {

    private final SkillPort port;

    public Skill create(UUID organizationId, Skill skill) {
        UUID id = UUID.randomUUID();
        return this.port.save(organizationId, new Skill(id, skill.name()));
    }

    public Skill update(UUID organizationId, Skill skill) {
        return this.port.save(organizationId, skill);
    }

    public void delete(UUID organizationId, UUID skillId) {
        this.port.deleteById(organizationId, skillId);
    }

    public Optional<Skill> findById(UUID organizationId, UUID id) {
        return this.port.findById(organizationId, id);
    }

    public List<Skill> findAll(UUID organizationId) {
        return this.port.findAll(organizationId);
    }
}
