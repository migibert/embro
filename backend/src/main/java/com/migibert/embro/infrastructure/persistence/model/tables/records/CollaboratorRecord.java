/*
 * This file is generated by jOOQ.
 */
package com.migibert.embro.infrastructure.persistence.model.tables.records;


import com.migibert.embro.infrastructure.persistence.model.tables.CollaboratorTable;

import java.time.LocalDate;
import java.util.UUID;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class CollaboratorRecord extends UpdatableRecordImpl<CollaboratorRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.collaborator.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.collaborator.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.collaborator.organization_id</code>.
     */
    public void setOrganizationId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.collaborator.organization_id</code>.
     */
    public UUID getOrganizationId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>public.collaborator.email</code>.
     */
    public void setEmail(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.collaborator.email</code>.
     */
    public String getEmail() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.collaborator.firstname</code>.
     */
    public void setFirstname(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.collaborator.firstname</code>.
     */
    public String getFirstname() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.collaborator.lastname</code>.
     */
    public void setLastname(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.collaborator.lastname</code>.
     */
    public String getLastname() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.collaborator.position</code>.
     */
    public void setPosition(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.collaborator.position</code>.
     */
    public String getPosition() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.collaborator.birth_date</code>.
     */
    public void setBirthDate(LocalDate value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.collaborator.birth_date</code>.
     */
    public LocalDate getBirthDate() {
        return (LocalDate) get(6);
    }

    /**
     * Setter for <code>public.collaborator.start_date</code>.
     */
    public void setStartDate(LocalDate value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.collaborator.start_date</code>.
     */
    public LocalDate getStartDate() {
        return (LocalDate) get(7);
    }

    /**
     * Setter for <code>public.collaborator.seniority_name</code>.
     */
    public void setSeniorityName(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.collaborator.seniority_name</code>.
     */
    public String getSeniorityName() {
        return (String) get(8);
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
     * Create a detached CollaboratorRecord
     */
    public CollaboratorRecord() {
        super(CollaboratorTable.COLLABORATOR);
    }

    /**
     * Create a detached, initialised CollaboratorRecord
     */
    public CollaboratorRecord(UUID id, UUID organizationId, String email, String firstname, String lastname, String position, LocalDate birthDate, LocalDate startDate, String seniorityName) {
        super(CollaboratorTable.COLLABORATOR);

        setId(id);
        setOrganizationId(organizationId);
        setEmail(email);
        setFirstname(firstname);
        setLastname(lastname);
        setPosition(position);
        setBirthDate(birthDate);
        setStartDate(startDate);
        setSeniorityName(seniorityName);
        resetChangedOnNotNull();
    }
}
