package com.migibert.embro.infrastructure.persistence.adapter;

import com.migibert.embro.domain.model.Seniority;
import com.migibert.embro.domain.port.SeniorityPort;
import com.migibert.embro.infrastructure.persistence.model.tables.records.OrganizationRecord;
import com.migibert.embro.infrastructure.persistence.model.tables.records.SeniorityRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.migibert.embro.infrastructure.persistence.model.Tables.ORGANIZATION;
import static com.migibert.embro.infrastructure.persistence.model.Tables.SENIORITY;

@Component
public class SeniorityAdapter implements SeniorityPort {

    private DSLContext context;
    public SeniorityAdapter(DSLContext context) {
        this.context = context;
    }

    public Seniority save(UUID organizationId, Seniority seniority) {
        SeniorityRecord record = this.context
                .insertInto(SENIORITY)
                .columns(SENIORITY.ID, SENIORITY.ORGANIZATION_ID, SENIORITY.NAME)
                .values(seniority.id(), organizationId, seniority.name())
                .returning()
                .fetchOne();

        return toDomainModel(record);
    }

    public void deleteById(UUID organizationId, UUID seniorityId) {
        this.context
                .deleteFrom(SENIORITY)
                .where(SENIORITY.ID.eq(seniorityId))
                .and(SENIORITY.ORGANIZATION_ID.eq(organizationId));
    }

    public Optional<Seniority> findById(UUID organizationId, UUID seniorityId) {
        int existing = this.context
                .selectCount()
                .from(SENIORITY)
                .where(SENIORITY.ID.eq(seniorityId))
                .and(SENIORITY.ORGANIZATION_ID.eq(organizationId))
                .fetchOne(0, int.class);

        if(existing == 0) {
            return Optional.empty();
        }
        SeniorityRecord record = this.context
                .selectFrom(SENIORITY)
                .where(SENIORITY.ID.eq(seniorityId))
                .and(SENIORITY.ORGANIZATION_ID.eq(organizationId))
                .fetchAny();
        return Optional.of(toDomainModel(record));
    }

    public List<Seniority> findAll(UUID organizationId) {
        return this.context
                .selectFrom(SENIORITY)
                .where(SENIORITY.ORGANIZATION_ID.eq(organizationId))
                .stream()
                .map(this::toDomainModel)
                .collect(Collectors.toList());
    }

    private Seniority toDomainModel(SeniorityRecord record) {
        return new Seniority(record.getId(), record.getName());
    }
}
