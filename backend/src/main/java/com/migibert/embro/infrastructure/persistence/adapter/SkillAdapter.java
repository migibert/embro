package com.migibert.embro.infrastructure.persistence.adapter;

import com.migibert.embro.domain.model.Skill;
import com.migibert.embro.domain.port.SkillPort;
import com.migibert.embro.infrastructure.persistence.model.tables.records.SkillRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.migibert.embro.infrastructure.persistence.model.Tables.SKILL;

@Component
public class SkillAdapter implements SkillPort {
    private DSLContext context;
    public SkillAdapter(DSLContext context) {
        this.context = context;
    }

    public Skill save(UUID organizationId, Skill skill) {
        SkillRecord record = this.context
                .insertInto(SKILL)
                .columns(SKILL.ID, SKILL.ORGANIZATION_ID, SKILL.NAME)
                .values(skill.id(), organizationId, skill.name())
                .returning()
                .fetchOne();

        return toDomainModel(record);
    }

    public void deleteById(UUID organizationId, UUID skillId) {
        this.context.deleteFrom(SKILL).where(SKILL.ID.eq(skillId)).and(SKILL.ORGANIZATION_ID.eq(organizationId)).execute();
    }

    public Optional<Skill> findById(UUID organizationId, UUID id) {
        int existing = this.context
                .selectCount()
                .from(SKILL)
                .where(SKILL.ID.eq(id))
                .and(SKILL.ORGANIZATION_ID.eq(organizationId))
                .fetchOne(0, int.class);

        if(existing == 0) {
            return Optional.empty();
        }
        SkillRecord record = this.context
                .selectFrom(SKILL)
                .where(SKILL.ID.eq(id))
                .and(SKILL.ORGANIZATION_ID.eq(organizationId))
                .fetchOne();
        return Optional.of(toDomainModel(record));
    }

    public List<Skill> findAll(UUID organizationId) {
        return this.context
                .selectFrom(SKILL)
                .where(SKILL.ORGANIZATION_ID.eq(organizationId))
                .fetch()
                .stream()
                .map(this::toDomainModel)
                .collect(Collectors.toList());
    }

    private Skill toDomainModel(SkillRecord record) {
        return new Skill(record.getId(), record.getName());
    }
}
