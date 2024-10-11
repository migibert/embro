package com.migibert.embro.infrastructure.persistence.adapter;

import com.migibert.embro.domain.model.Position;
import com.migibert.embro.domain.port.PositionPort;
import com.migibert.embro.infrastructure.persistence.model.tables.records.PositionRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.migibert.embro.infrastructure.persistence.model.Tables.POSITION;

@Component
public class PositionAdapter implements PositionPort {
    private DSLContext context;
    public PositionAdapter(DSLContext context) {
        this.context = context;
    }

    public Position save(UUID organizationId, Position position) {
        PositionRecord record = this.context
                .insertInto(POSITION)
                .columns(POSITION.ID, POSITION.ORGANIZATION_ID, POSITION.NAME)
                .values(position.id(), organizationId, position.name())
                .returning()
                .fetchOne();

        return toDomainModel(record);
    }

    public void deleteById(UUID organizationId, UUID id) {
        this.context.deleteFrom(POSITION).where(POSITION.ID.eq(id)).and(POSITION.ORGANIZATION_ID.eq(organizationId)).execute();
    }

    public Optional<Position> findById(UUID organizationId, UUID id) {
        int existing = this.context
                .selectCount()
                .from(POSITION)
                .where(POSITION.ID.eq(id))
                .and(POSITION.ORGANIZATION_ID.eq(organizationId))
                .fetchOne(0, int.class);

        if(existing == 0) {
            return Optional.empty();
        }
        PositionRecord record = this.context
                .selectFrom(POSITION)
                .where(POSITION.ID.eq(id))
                .and(POSITION.ORGANIZATION_ID.eq(organizationId))
                .fetchOne();
        return Optional.of(toDomainModel(record));
    }

    public List<Position> findAll(UUID organizationId) {
        return this.context
                .selectFrom(POSITION)
                .where(POSITION.ORGANIZATION_ID.eq(organizationId))
                .fetch()
                .stream()
                .map(this::toDomainModel)
                .collect(Collectors.toList());
    }

    private Position toDomainModel(PositionRecord record) {
        return new Position(record.getId(), record.getName());
    }
}
