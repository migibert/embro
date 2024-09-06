package com.migibert.embro.infrastructure.persistence.adapter;

import com.migibert.embro.domain.model.Organization;
import com.migibert.embro.domain.port.OrganizationPort;
import com.migibert.embro.infrastructure.persistence.model.tables.records.OrganizationRecord;
import org.jooq.DSLContext;
import org.jooq.impl.QOM;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.migibert.embro.infrastructure.persistence.model.Tables.*;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.or;

@Component
public class OrganizationAdapter implements OrganizationPort {

    private DSLContext context;
    public OrganizationAdapter(DSLContext context) {
        this.context = context;
    }

    public Organization save(Organization organization) {
        OrganizationRecord record = this.context
                .insertInto(ORGANIZATION)
                .columns(ORGANIZATION.ID, ORGANIZATION.NAME)
                .values(organization.id(), organization.name())
                .returning()
                .fetchOne();

        return toDomainModel(record);
    }

    @Transactional
    public void deleteById(UUID organizationId) {
        //TODO
        this.context.deleteFrom(TEAM_COLLABORATOR).where(TEAM_COLLABORATOR.ORGANIZATION_ID.eq(organizationId)).execute();
        this.context.deleteFrom(TEAM).where(TEAM.ORGANIZATION_ID.eq(organizationId)).execute();
        this.context.deleteFrom(COLLABORATOR_SKILL).where(COLLABORATOR_SKILL.ORGANIZATION_ID.eq(organizationId)).execute();
        this.context.deleteFrom(COLLABORATOR).where(COLLABORATOR.ORGANIZATION_ID.eq(organizationId)).execute();
        this.context.deleteFrom(SKILL).where(SKILL.ORGANIZATION_ID.eq(organizationId)).execute();
        this.context.deleteFrom(SENIORITY).where(SENIORITY.ORGANIZATION_ID.eq(organizationId)).execute();
        this.context.deleteFrom(ROLE).where(ROLE.ORGANIZATION_ID.eq(organizationId)).execute();
        this.context.deleteFrom(USER_ORGANIZATION).where(USER_ORGANIZATION.ORGANIZATION_ID.eq(organizationId)).execute();
        this.context.deleteFrom(ORGANIZATION).where(ORGANIZATION.ID.eq(organizationId)).execute();
    }

    public Optional<Organization> findById(UUID organizationId) {
        int existing = this.context
                .selectCount()
                .from(ORGANIZATION)
                .where(ORGANIZATION.ID.eq(organizationId))
                .fetchOne(0, int.class);
        if(existing == 0) {
            return Optional.empty();
        }
        OrganizationRecord record = this.context
                .selectFrom(ORGANIZATION)
                .where(ORGANIZATION.ID.eq(organizationId))
                .fetchAny();
        return Optional.of(toDomainModel(record));
    }

    public List<Organization> findAll() {
        return this.context.selectFrom(ORGANIZATION).stream().map(this::toDomainModel).collect(Collectors.toList());
    }

    @Override
    public Set<Organization> findByIds(List<UUID> ids) {
        return this.context.selectFrom(ORGANIZATION).where(ORGANIZATION.ID.in(ids)).stream().map(this::toDomainModel).collect(Collectors.toSet());
    }


    private Organization toDomainModel(OrganizationRecord record) {
        return new Organization(record.getId(), record.getName());
    }
}
