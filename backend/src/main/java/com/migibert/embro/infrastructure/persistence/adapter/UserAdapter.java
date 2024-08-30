package com.migibert.embro.infrastructure.persistence.adapter;

import com.migibert.embro.domain.port.UserPort;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static com.migibert.embro.infrastructure.persistence.model.Tables.USER_ORGANIZATION;

@Component
public class UserAdapter implements UserPort {

    private DSLContext context;
    public UserAdapter(DSLContext context) {
        this.context = context;
    }

    public boolean isRelated(String userId, UUID organizationId) {
        return this.context
                .selectFrom(USER_ORGANIZATION)
                .where(USER_ORGANIZATION.ORGANIZATION_ID.eq(organizationId))
                .and(USER_ORGANIZATION.USER_ID.eq(userId))
                .fetchOptional()
                .isPresent();
    }

    @Override
    public void relate(String userId, UUID organizationId) {
        this.context
            .insertInto(USER_ORGANIZATION)
            .columns(USER_ORGANIZATION.ORGANIZATION_ID, USER_ORGANIZATION.USER_ID)
            .values(organizationId, userId)
            .execute();
    }

    @Override
    public List<UUID> getRelations(String userId) {
        return this.context
                .selectFrom(USER_ORGANIZATION)
                .where(USER_ORGANIZATION.USER_ID.eq(userId))
                .fetch(USER_ORGANIZATION.ORGANIZATION_ID);
    }
}
