/*
 * This file is generated by jOOQ.
 */
package com.migibert.embro.infrastructure.persistence.model.tables.records;


import com.migibert.embro.infrastructure.persistence.model.tables.UserOrganizationTable;

import java.util.UUID;

import org.jooq.Record2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserOrganizationRecord extends UpdatableRecordImpl<UserOrganizationRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.user_organization.user_id</code>.
     */
    public void setUserId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.user_organization.user_id</code>.
     */
    public String getUserId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.user_organization.organization_id</code>.
     */
    public void setOrganizationId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.user_organization.organization_id</code>.
     */
    public UUID getOrganizationId() {
        return (UUID) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<String, UUID> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserOrganizationRecord
     */
    public UserOrganizationRecord() {
        super(UserOrganizationTable.USER_ORGANIZATION);
    }

    /**
     * Create a detached, initialised UserOrganizationRecord
     */
    public UserOrganizationRecord(String userId, UUID organizationId) {
        super(UserOrganizationTable.USER_ORGANIZATION);

        setUserId(userId);
        setOrganizationId(organizationId);
        resetChangedOnNotNull();
    }
}
