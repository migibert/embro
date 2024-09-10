/*
 * This file is generated by jOOQ.
 */
package com.migibert.embro.infrastructure.persistence.model.tables.records;


import com.migibert.embro.infrastructure.persistence.model.tables.RoleTable;

import java.util.UUID;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RoleRecord extends UpdatableRecordImpl<RoleRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.role.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.role.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.role.organization_id</code>.
     */
    public void setOrganizationId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.role.organization_id</code>.
     */
    public UUID getOrganizationId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>public.role.name</code>.
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.role.name</code>.
     */
    public String getName() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached RoleRecord
     */
    public RoleRecord() {
        super(RoleTable.ROLE);
    }

    /**
     * Create a detached, initialised RoleRecord
     */
    public RoleRecord(UUID id, UUID organizationId, String name) {
        super(RoleTable.ROLE);

        setId(id);
        setOrganizationId(organizationId);
        setName(name);
        resetChangedOnNotNull();
    }
}
