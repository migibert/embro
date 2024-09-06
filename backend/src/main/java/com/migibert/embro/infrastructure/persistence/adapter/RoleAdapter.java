package com.migibert.embro.infrastructure.persistence.adapter;

import com.migibert.embro.domain.model.Role;
import com.migibert.embro.domain.port.RolePort;
import com.migibert.embro.infrastructure.persistence.model.tables.records.RoleRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.migibert.embro.infrastructure.persistence.model.Tables.ROLE;
import static com.migibert.embro.infrastructure.persistence.model.Tables.SKILL;

@Component
public class RoleAdapter implements RolePort {
    private DSLContext context;
    public RoleAdapter(DSLContext context) {
        this.context = context;
    }

    public Role save(UUID organizationId, Role role) {
        RoleRecord record = this.context
                .insertInto(ROLE)
                .columns(ROLE.ID, ROLE.ORGANIZATION_ID, ROLE.NAME)
                .values(role.id(), organizationId, role.name())
                .returning()
                .fetchOne();

        return toDomainModel(record);
    }

    public void deleteById(UUID organizationId, UUID roleId) {
        this.context.deleteFrom(ROLE).where(ROLE.ID.eq(roleId)).and(ROLE.ORGANIZATION_ID.eq(organizationId)).execute();
    }

    public Optional<Role> findById(UUID organizationId, UUID id) {
        int existing = this.context
                .selectCount()
                .from(ROLE)
                .where(ROLE.ID.eq(id))
                .and(ROLE.ORGANIZATION_ID.eq(organizationId))
                .fetchOne(0, int.class);

        if(existing == 0) {
            return Optional.empty();
        }
        RoleRecord record = this.context
                .selectFrom(ROLE)
                .where(ROLE.ID.eq(id))
                .and(ROLE.ORGANIZATION_ID.eq(organizationId))
                .fetchOne();
        return Optional.of(toDomainModel(record));
    }

    public List<Role> findAll(UUID organizationId) {
        return this.context
                .selectFrom(ROLE)
                .where(ROLE.ORGANIZATION_ID.eq(organizationId))
                .fetch()
                .stream()
                .map(this::toDomainModel)
                .collect(Collectors.toList());
    }

    private Role toDomainModel(RoleRecord record) {
        return new Role(record.getId(), record.getName());
    }
}
