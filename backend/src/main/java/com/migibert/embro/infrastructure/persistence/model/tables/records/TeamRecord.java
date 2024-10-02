/*
 * This file is generated by jOOQ.
 */
package com.migibert.embro.infrastructure.persistence.model.tables.records;


import com.migibert.embro.infrastructure.persistence.model.tables.TeamTable;

import java.util.UUID;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class TeamRecord extends UpdatableRecordImpl<TeamRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.team.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.team.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.team.organization_id</code>.
     */
    public void setOrganizationId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.team.organization_id</code>.
     */
    public UUID getOrganizationId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>public.team.name</code>.
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.team.name</code>.
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.team.mission</code>.
     */
    public void setMission(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.team.mission</code>.
     */
    public String getMission() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.team.email</code>.
     */
    public void setEmail(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.team.email</code>.
     */
    public String getEmail() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.team.instant_message</code>.
     */
    public void setInstantMessage(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.team.instant_message</code>.
     */
    public String getInstantMessage() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.team.phone</code>.
     */
    public void setPhone(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.team.phone</code>.
     */
    public String getPhone() {
        return (String) get(6);
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
     * Create a detached TeamRecord
     */
    public TeamRecord() {
        super(TeamTable.TEAM);
    }

    /**
     * Create a detached, initialised TeamRecord
     */
    public TeamRecord(UUID id, UUID organizationId, String name, String mission, String email, String instantMessage, String phone) {
        super(TeamTable.TEAM);

        setId(id);
        setOrganizationId(organizationId);
        setName(name);
        setMission(mission);
        setEmail(email);
        setInstantMessage(instantMessage);
        setPhone(phone);
        resetChangedOnNotNull();
    }
}
