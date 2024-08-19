package com.migibert.embro.infrastructure.persistence.adapter;

import com.migibert.embro.domain.model.Seniority;
import com.migibert.embro.domain.model.Team;
import com.migibert.embro.domain.port.TeamPort;
import com.migibert.embro.infrastructure.persistence.model.tables.records.SeniorityRecord;
import com.migibert.embro.infrastructure.persistence.model.tables.records.SkillRecord;
import com.migibert.embro.infrastructure.persistence.model.tables.records.TeamRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.migibert.embro.infrastructure.persistence.model.Tables.*;
import static com.migibert.embro.infrastructure.persistence.model.Tables.SKILL;

@Component
public class TeamAdapter implements TeamPort {

    private DSLContext context;
    public TeamAdapter(DSLContext context) {
        this.context = context;
    }

    public Team create(UUID organizationId, Team team) {
        TeamRecord record = this.context
                .insertInto(TEAM)
                .columns(TEAM.ID, TEAM.ORGANIZATION_ID, TEAM.NAME)
                .values(team.id(), organizationId, team.name())
                .returning()
                .fetchOne();

        return toDomainModel(record);
    }

    public Team update(UUID organizationId, Team team) {
        return null;
    }

    public void deleteById(UUID organizationId, UUID teamId) {
        this.context.deleteFrom(TEAM).where(TEAM.ID.eq(teamId)).and(SKILL.ORGANIZATION_ID.eq(organizationId));
    }

    public Optional<Team> findById(UUID organizationId, UUID teamId) {
        int existing = this.context
                .selectCount()
                .from(TEAM)
                .where(TEAM.ID.eq(teamId))
                .and(TEAM.ORGANIZATION_ID.eq(organizationId))
                .fetchOne(0, int.class);

        if(existing == 0) {
            return Optional.empty();
        }
        TeamRecord record = this.context
                .selectFrom(TEAM)
                .where(TEAM.ID.eq(teamId))
                .$where(TEAM.ORGANIZATION_ID.eq(organizationId))
                .fetchOne();
        return Optional.of(toDomainModel(record));    }

    public List<Team> findAll(UUID organizationId) {
        return this.context
                .selectFrom(TEAM)
                .where(TEAM.ORGANIZATION_ID.eq(organizationId))
                .fetch()
                .stream()
                .map(this::toDomainModel)
                .collect(Collectors.toList());
    }

    private Team toDomainModel(TeamRecord record) {
        return new Team(record.getId(), record.getName());
    }

}
