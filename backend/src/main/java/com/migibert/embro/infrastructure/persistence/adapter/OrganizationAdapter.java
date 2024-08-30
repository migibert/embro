package com.migibert.embro.infrastructure.persistence.adapter;

import com.migibert.embro.domain.model.Organization;
import com.migibert.embro.domain.port.OrganizationPort;
import com.migibert.embro.infrastructure.persistence.model.tables.records.OrganizationRecord;
import org.jooq.DSLContext;
import org.jooq.impl.QOM;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.migibert.embro.infrastructure.persistence.model.Tables.ORGANIZATION;
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

    public void deleteById(UUID organizationId) {
        this.context.deleteFrom(ORGANIZATION).where(ORGANIZATION.ID.eq(organizationId));
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
