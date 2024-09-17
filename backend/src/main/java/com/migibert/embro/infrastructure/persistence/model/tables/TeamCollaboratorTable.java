/*
 * This file is generated by jOOQ.
 */
package com.migibert.embro.infrastructure.persistence.model.tables;


import com.migibert.embro.infrastructure.persistence.model.Keys;
import com.migibert.embro.infrastructure.persistence.model.Public;
import com.migibert.embro.infrastructure.persistence.model.tables.CollaboratorTable.CollaboratorPath;
import com.migibert.embro.infrastructure.persistence.model.tables.OrganizationTable.OrganizationPath;
import com.migibert.embro.infrastructure.persistence.model.tables.TeamTable.TeamPath;
import com.migibert.embro.infrastructure.persistence.model.tables.records.TeamCollaboratorRecord;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.InverseForeignKey;
import org.jooq.Name;
import org.jooq.Path;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class TeamCollaboratorTable extends TableImpl<TeamCollaboratorRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.team_collaborator</code>
     */
    public static final TeamCollaboratorTable TEAM_COLLABORATOR = new TeamCollaboratorTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TeamCollaboratorRecord> getRecordType() {
        return TeamCollaboratorRecord.class;
    }

    /**
     * The column <code>public.team_collaborator.organization_id</code>.
     */
    public final TableField<TeamCollaboratorRecord, UUID> ORGANIZATION_ID = createField(DSL.name("organization_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.team_collaborator.team_id</code>.
     */
    public final TableField<TeamCollaboratorRecord, UUID> TEAM_ID = createField(DSL.name("team_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.team_collaborator.collaborator_id</code>.
     */
    public final TableField<TeamCollaboratorRecord, UUID> COLLABORATOR_ID = createField(DSL.name("collaborator_id"), SQLDataType.UUID.nullable(false), this, "");

    private TeamCollaboratorTable(Name alias, Table<TeamCollaboratorRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private TeamCollaboratorTable(Name alias, Table<TeamCollaboratorRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.team_collaborator</code> table reference
     */
    public TeamCollaboratorTable(String alias) {
        this(DSL.name(alias), TEAM_COLLABORATOR);
    }

    /**
     * Create an aliased <code>public.team_collaborator</code> table reference
     */
    public TeamCollaboratorTable(Name alias) {
        this(alias, TEAM_COLLABORATOR);
    }

    /**
     * Create a <code>public.team_collaborator</code> table reference
     */
    public TeamCollaboratorTable() {
        this(DSL.name("team_collaborator"), null);
    }

    public <O extends Record> TeamCollaboratorTable(Table<O> path, ForeignKey<O, TeamCollaboratorRecord> childPath, InverseForeignKey<O, TeamCollaboratorRecord> parentPath) {
        super(path, childPath, parentPath, TEAM_COLLABORATOR);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class TeamCollaboratorPath extends TeamCollaboratorTable implements Path<TeamCollaboratorRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> TeamCollaboratorPath(Table<O> path, ForeignKey<O, TeamCollaboratorRecord> childPath, InverseForeignKey<O, TeamCollaboratorRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private TeamCollaboratorPath(Name alias, Table<TeamCollaboratorRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public TeamCollaboratorPath as(String alias) {
            return new TeamCollaboratorPath(DSL.name(alias), this);
        }

        @Override
        public TeamCollaboratorPath as(Name alias) {
            return new TeamCollaboratorPath(alias, this);
        }

        @Override
        public TeamCollaboratorPath as(Table<?> alias) {
            return new TeamCollaboratorPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<TeamCollaboratorRecord> getPrimaryKey() {
        return Keys.TEAM_COLLABORATOR_PKEY;
    }

    @Override
    public List<ForeignKey<TeamCollaboratorRecord, ?>> getReferences() {
        return Arrays.asList(Keys.TEAM_COLLABORATOR__FK_TEAM_COLLABORATOR_ORGANIZATION, Keys.TEAM_COLLABORATOR__FK_TEAM_COLLABORATOR_TEAM, Keys.TEAM_COLLABORATOR__FK_TEAM_COLLABORATOR_COLLABORATOR);
    }

    private transient OrganizationPath _organization;

    /**
     * Get the implicit join path to the <code>public.organization</code> table.
     */
    public OrganizationPath organization() {
        if (_organization == null)
            _organization = new OrganizationPath(this, Keys.TEAM_COLLABORATOR__FK_TEAM_COLLABORATOR_ORGANIZATION, null);

        return _organization;
    }

    private transient TeamPath _team;

    /**
     * Get the implicit join path to the <code>public.team</code> table.
     */
    public TeamPath team() {
        if (_team == null)
            _team = new TeamPath(this, Keys.TEAM_COLLABORATOR__FK_TEAM_COLLABORATOR_TEAM, null);

        return _team;
    }

    private transient CollaboratorPath _collaborator;

    /**
     * Get the implicit join path to the <code>public.collaborator</code> table.
     */
    public CollaboratorPath collaborator() {
        if (_collaborator == null)
            _collaborator = new CollaboratorPath(this, Keys.TEAM_COLLABORATOR__FK_TEAM_COLLABORATOR_COLLABORATOR, null);

        return _collaborator;
    }

    @Override
    public TeamCollaboratorTable as(String alias) {
        return new TeamCollaboratorTable(DSL.name(alias), this);
    }

    @Override
    public TeamCollaboratorTable as(Name alias) {
        return new TeamCollaboratorTable(alias, this);
    }

    @Override
    public TeamCollaboratorTable as(Table<?> alias) {
        return new TeamCollaboratorTable(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public TeamCollaboratorTable rename(String name) {
        return new TeamCollaboratorTable(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TeamCollaboratorTable rename(Name name) {
        return new TeamCollaboratorTable(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public TeamCollaboratorTable rename(Table<?> name) {
        return new TeamCollaboratorTable(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public TeamCollaboratorTable where(Condition condition) {
        return new TeamCollaboratorTable(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public TeamCollaboratorTable where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public TeamCollaboratorTable where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public TeamCollaboratorTable where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public TeamCollaboratorTable where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public TeamCollaboratorTable where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public TeamCollaboratorTable where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public TeamCollaboratorTable where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public TeamCollaboratorTable whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public TeamCollaboratorTable whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
