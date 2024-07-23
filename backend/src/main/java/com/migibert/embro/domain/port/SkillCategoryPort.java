package com.migibert.embro.domain.port;

import com.migibert.embro.domain.model.SkillCategory;

import java.util.List;
import java.util.UUID;

public interface SkillCategoryPort {
    SkillCategory save(SkillCategory category);

    void delete(UUID categoryId);

    SkillCategory findById(UUID categoryId);

    List<SkillCategory> findAll();
}
