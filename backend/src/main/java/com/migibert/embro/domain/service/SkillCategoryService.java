package com.migibert.embro.domain.service;

import com.migibert.embro.domain.model.SkillCategory;
import com.migibert.embro.domain.port.SkillCategoryPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class SkillCategoryService {

    private final SkillCategoryPort port;

    public SkillCategory save(SkillCategory category) {
        return this.port.save(category);
    }

    public void delete(UUID categoryId) {
        this.port.delete(categoryId);
    }

    public SkillCategory findById(UUID categoryId) {
        return this.port.findById(categoryId);
    }

    public List<SkillCategory> findAll() {
        return this.port.findAll();
    }
}
