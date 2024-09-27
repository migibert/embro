package com.migibert.embro.infrastructure.persistence.adapter;

import static com.migibert.embro.infrastructure.persistence.model.Tables.*;
import static org.jooq.impl.DSL.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.migibert.embro.domain.model.Collaborator;
import com.migibert.embro.domain.model.Member;
import com.migibert.embro.domain.model.SkillLevel;
import com.migibert.embro.infrastructure.persistence.model.tables.records.SkillRecord;
import jnr.a64asm.Mem;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.jooq.*;
import org.springframework.stereotype.Component;

import com.migibert.embro.domain.model.Team;
import com.migibert.embro.domain.port.TeamPort;
import com.migibert.embro.infrastructure.persistence.model.tables.records.TeamRecord;

@Component
@Slf4j
public class TeamAdapter implements TeamPort {

    private DSLContext context;
    public TeamAdapter(DSLContext context) {
        this.context = context;
    }

    public Team create(UUID organizationId, Team team) {
        TeamRecord record = toPersistenceModel(organizationId, team);
        this.context.executeInsert(record);
        return toDomainModel(record);
    }

    public Team update(UUID organizationId, Team team) {
        TeamRecord record = toPersistenceModel(organizationId, team);
        this.context.executeUpdate(record);
        return toDomainModel(record);

    }

    public void deleteById(UUID organizationId, UUID teamId) {
        this.context.deleteFrom(TEAM).where(TEAM.ID.eq(teamId)).and(TEAM.ORGANIZATION_ID.eq(organizationId)).execute();
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
                .and(TEAM.ORGANIZATION_ID.eq(organizationId))
                .fetchOne();
        return Optional.of(toDomainModel(record));
    }

    public Set<Team> findAll(UUID organizationId) {
        return this.context
                .selectFrom(TEAM)
                .where(TEAM.ORGANIZATION_ID.eq(organizationId))
                .fetch()
                .stream()
                .map(this::toDomainModel)
                .collect(Collectors.toSet());
    }

    @Override
    public Member addMember(UUID organizationId, UUID teamId, UUID collaboratorId, boolean keyPlayer) {
        this.context.insertInto(TEAM_COLLABORATOR)
                .columns(TEAM_COLLABORATOR.ORGANIZATION_ID, TEAM_COLLABORATOR.COLLABORATOR_ID, TEAM_COLLABORATOR.TEAM_ID, TEAM_COLLABORATOR.KEY_PLAYER)
                .values(organizationId, collaboratorId, teamId, keyPlayer)
                .execute();
        Optional<Member> found = this.findMember(organizationId, teamId, collaboratorId);
        if(found.isEmpty()) {
            log.error("{} should have been added to {} in organization {} right now", collaboratorId, teamId, organizationId);
            throw new IllegalStateException();
        }
        return found.get();
    }

    @Override
    public void removeMember(UUID organizationId, UUID teamId, UUID collaboratorId) {
        this.context.deleteFrom(TEAM_COLLABORATOR)
                .where(TEAM_COLLABORATOR.TEAM_ID.eq(teamId))
                .and(TEAM_COLLABORATOR.COLLABORATOR_ID.eq(collaboratorId))
                .and(TEAM_COLLABORATOR.ORGANIZATION_ID.eq(organizationId))
                .execute();
    }

    @Override
    public Set<Member> listMembers(UUID organizationId, UUID teamId) {
        return this.listMembersBy(organizationId, teamId);
    }

    private Set<Member> listMembersBy(UUID organizationId, UUID teamId, Condition... conditions) {
        Condition condition = TEAM_COLLABORATOR.ORGANIZATION_ID.eq(organizationId).and(TEAM_COLLABORATOR.TEAM_ID.eq(teamId));
        for(Condition c: conditions) {
            condition = condition.and(c);
        }
        return this.context
                .select(
                        COLLABORATOR.ID,
                        COLLABORATOR.FIRSTNAME,
                        COLLABORATOR.LASTNAME,
                        COLLABORATOR.EMAIL,
                        COLLABORATOR.ROLE,
                        COLLABORATOR.SENIORITY_NAME,
                        COLLABORATOR.START_DATE,
                        TEAM_COLLABORATOR.KEY_PLAYER
                )
                .from(TEAM_COLLABORATOR)
                .join(COLLABORATOR)
                .on(TEAM_COLLABORATOR.COLLABORATOR_ID.eq(COLLABORATOR.ID))
                .and(TEAM_COLLABORATOR.ORGANIZATION_ID.eq(COLLABORATOR.ORGANIZATION_ID))
                .join(TEAM)
                .on(TEAM_COLLABORATOR.TEAM_ID.eq(TEAM.ID))
                .and(TEAM_COLLABORATOR.ORGANIZATION_ID.eq(TEAM.ORGANIZATION_ID))
                .where(condition)
                .fetch()
                .map(this::toMemberDomainModel)
                .stream().collect(Collectors.toSet());
    }

    private Optional<Member> findMember(UUID organizationId, UUID teamId, UUID collaboratorId) {
        Set<Member> found = listMembersBy(organizationId, teamId, TEAM_COLLABORATOR.COLLABORATOR_ID.eq(collaboratorId));
        if(found.size() == 0) {
            return Optional.empty();
        }
        if(found.size() > 1) {
            log.warn("There should be only one Member combining organizationId {}, teamId {} and collaboratorId {}", organizationId, teamId, collaboratorId);
        }
        return found.stream().findFirst();
    }

    private Member toMemberDomainModel(Record8<UUID, String, String, String, String, String, LocalDate, Boolean> data) {
        UUID collaboratorId = data.component1();
        String firstname = data.component2();
        String lastname = data.component3();
        String email = data.component4();
        String role = data.component5();
        String seniority = data.component6();
        LocalDate startDate = data.component7();
        Boolean keyPlayer = data.component8();
        return new Member(collaboratorId, firstname, lastname, email, role, seniority, startDate, keyPlayer);
    }

    private Team toDomainModel(TeamRecord record) {
        return new Team(
                record.getId(),
                record.getName(),
                record.getMission(),
                record.getEmail(),
                record.getInstantMessage(),
                record.getPhone()
        );
    }

    private TeamRecord toPersistenceModel(UUID organizationId, Team team) {
        return new TeamRecord(
                team.id(),
                organizationId,
                team.name(),
                team.mission(),
                team.email(),
                team.instantMessage(),
                team.phone()
        );
    }

}
