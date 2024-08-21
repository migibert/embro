package com.migibert.embro.infrastructure.persistence.adapter;

import com.migibert.embro.domain.model.Collaborator;
import com.migibert.embro.domain.model.Skill;
import com.migibert.embro.domain.model.SkillLevel;
import com.migibert.embro.domain.port.CollaboratorPort;
import com.migibert.embro.infrastructure.persistence.model.tables.records.CollaboratorRecord;
import com.migibert.embro.infrastructure.persistence.model.tables.records.CollaboratorSkillRecord;
import com.migibert.embro.infrastructure.persistence.model.tables.records.SkillRecord;
import org.jooq.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.migibert.embro.infrastructure.persistence.model.Tables.*;
import static org.jooq.impl.DSL.*;


@Component
public class CollaboratorAdapter implements CollaboratorPort {

    private DSLContext context;
    public CollaboratorAdapter(DSLContext context) {
        this.context = context;
    }

    @Override
    @Transactional
    public Collaborator create(UUID organizationId, Collaborator collaborator) {
        this.context
            .insertInto(COLLABORATOR)
            .columns(COLLABORATOR.ID, COLLABORATOR.ORGANIZATION_ID, COLLABORATOR.FIRSTNAME, COLLABORATOR.LASTNAME, COLLABORATOR.BIRTH_DATE, COLLABORATOR.EMAIL, COLLABORATOR.ROLE, COLLABORATOR.SENIORITY_NAME, COLLABORATOR.START_DATE)
            .values(collaborator.id(), organizationId, collaborator.firstname(), collaborator.lastname(), collaborator.birthDate(), collaborator.email(), collaborator.role(), collaborator.seniority(), collaborator.startDate())
            .returning()
            .fetchOne();

        List<CollaboratorSkillRecord> records = collaborator.skills()
            .stream()
            .map(skillLevel -> new CollaboratorSkillRecord(collaborator.id(), skillLevel.skill().id(), skillLevel.proficiency()))
            .collect(Collectors.toList());

        this.context.batchInsert(records).execute();
        return collaborator;
    }

    @Override
    public Collaborator update(UUID organizationId, Collaborator collaborator) {
        return null;
    }

    @Override
    @Transactional
    public void deleteById(UUID organizationId, UUID collaboratorId) {
        this.context.deleteFrom(COLLABORATOR).where(COLLABORATOR.ID.eq(collaboratorId)).and(COLLABORATOR.ORGANIZATION_ID.eq(organizationId));
        this.context.deleteFrom(COLLABORATOR_SKILL).where(COLLABORATOR_SKILL.COLLABORATOR_ID.eq(collaboratorId));
        this.context.deleteFrom(TEAM_COLLABORATOR).where(TEAM_COLLABORATOR.COLLABORATOR_ID.eq(collaboratorId));
    }

    @Override
    public Optional<Collaborator> findById(UUID organizationId, UUID collaboratorId) {
        Condition c = COLLABORATOR.ID.eq(collaboratorId);
        Set<Collaborator> collaborators = findByCondition(organizationId, c);
        return collaborators.stream().findFirst();
    }

    @Override
    public Set<Collaborator> findAll(UUID organizationId) {
        Condition c = noCondition();
        return findByCondition(organizationId, c);
    }

    private Collaborator toDomainModel(Record10<UUID, UUID, String, String, LocalDate, String, String, String, LocalDate, Result<Record2<SkillRecord, Integer>>> data) {
        UUID id = data.component1();
        String firstName = data.component3();
        String lastName = data.component4();
        LocalDate birthDate = data.component5();
        String email = data.component6();
        String role = data.component7();
        String seniority = data.component8();
        LocalDate startDate = data.component9();
        Set<SkillLevel> skills = data.component10().map(this::toDomainModel).stream().collect(Collectors.toSet());
        return new Collaborator(id, email, firstName, lastName, role, birthDate, startDate, seniority, skills);
    }

    private SkillLevel toDomainModel(Record2<SkillRecord, Integer> record) {
        SkillRecord skillRecord = record.component1();
        Integer proficiency = record.component2();
        return new SkillLevel(
            new Skill(
                skillRecord.getId(),
                skillRecord.getName()
            ),
            proficiency
        );
    }


    @Override
    public Set<Collaborator> findByTeam(UUID organizationId, UUID teamID) {
        Condition c = COLLABORATOR.team().ID.eq(teamID);
        return findByCondition(organizationId, c);
    }

    @Override
    public Set<Collaborator> findBySkill(UUID organizationId, UUID skillId) {
        Condition c = COLLABORATOR.skill().ID.eq(skillId);
        return findByCondition(organizationId, c);
    }

    @Override
    public Set<Collaborator> findByName(UUID organizationId, String name) {
        Condition c = or(COLLABORATOR.FIRSTNAME.eq(name), COLLABORATOR.LASTNAME.eq(name));
        return findByCondition(organizationId, c);
    }

    private Set<Collaborator> findByCondition(UUID organizationId, Condition condition) {
        condition = condition.and(COLLABORATOR.ORGANIZATION_ID.eq(organizationId));
        return this.context
            .select(
                COLLABORATOR.ID,
                COLLABORATOR.ORGANIZATION_ID,
                COLLABORATOR.FIRSTNAME,
                COLLABORATOR.LASTNAME,
                COLLABORATOR.BIRTH_DATE,
                COLLABORATOR.EMAIL,
                COLLABORATOR.ROLE,
                COLLABORATOR.SENIORITY_NAME,
                COLLABORATOR.START_DATE,
                multiset(
                    select(
                        COLLABORATOR_SKILL.skill(),
                        COLLABORATOR_SKILL.PROFICIENCY
                    )
                    .from(COLLABORATOR_SKILL)
                    .where(COLLABORATOR_SKILL.COLLABORATOR_ID.eq(COLLABORATOR.ID))
                )
            )
            .from(COLLABORATOR)
            .where(condition)
            .fetch()
            .map(this::toDomainModel)
            .stream()
            .collect(Collectors.toSet());
    }
}
