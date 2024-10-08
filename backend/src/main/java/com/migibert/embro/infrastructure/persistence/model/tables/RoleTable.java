/*
 * This file is generated by jOOQ.
 */
package com.migibert.embro.infrastructure.persistence.model.tables;


import com.migibert.embro.infrastructure.persistence.model.Keys;
import com.migibert.embro.infrastructure.persistence.model.Public;
import com.migibert.embro.infrastructure.persistence.model.tables.OrganizationTable.OrganizationPath;
import com.migibert.embro.infrastructure.persistence.model.tables.records.RoleRecord;

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
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RoleTable extends TableImpl<RoleRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.role</code>
     */
    public static final RoleTable ROLE = new RoleTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RoleRecord> getRecordType() {
        return RoleRecord.class;
    }

    /**
     * The column <code>public.role.id</code>.
     */
    public final TableField<RoleRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.role.organization_id</code>.
     */
    public final TableField<RoleRecord, UUID> ORGANIZATION_ID = createField(DSL.name("organization_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.role.name</code>.
     */
    public final TableField<RoleRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(100).nullable(false), this, "");

    private RoleTable(Name alias, Table<RoleRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private RoleTable(Name alias, Table<RoleRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.role</code> table reference
     */
    public RoleTable(String alias) {
        this(DSL.name(alias), ROLE);
    }

    /**
     * Create an aliased <code>public.role</code> table reference
     */
    public RoleTable(Name alias) {
        this(alias, ROLE);
    }

    /**
     * Create a <code>public.role</code> table reference
     */
    public RoleTable() {
        this(DSL.name("role"), null);
    }

    public <O extends Record> RoleTable(Table<O> path, ForeignKey<O, RoleRecord> childPath, InverseForeignKey<O, RoleRecord> parentPath) {
        super(path, childPath, parentPath, ROLE);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class RolePath extends RoleTable implements Path<RoleRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> RolePath(Table<O> path, ForeignKey<O, RoleRecord> childPath, InverseForeignKey<O, RoleRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private RolePath(Name alias, Table<RoleRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public RolePath as(String alias) {
            return new RolePath(DSL.name(alias), this);
        }

        @Override
        public RolePath as(Name alias) {
            return new RolePath(alias, this);
        }

        @Override
        public RolePath as(Table<?> alias) {
            return new RolePath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<RoleRecord> getPrimaryKey() {
        return Keys.ROLE_PKEY;
    }

    @Override
    public List<ForeignKey<RoleRecord, ?>> getReferences() {
        return Arrays.asList(Keys.ROLE__FK_ROLE_ORGANIZATION);
    }

    private transient OrganizationPath _organization;

    /**
     * Get the implicit join path to the <code>public.organization</code> table.
     */
    public OrganizationPath organization() {
        if (_organization == null)
            _organization = new OrganizationPath(this, Keys.ROLE__FK_ROLE_ORGANIZATION, null);

        return _organization;
    }

    @Override
    public RoleTable as(String alias) {
        return new RoleTable(DSL.name(alias), this);
    }

    @Override
    public RoleTable as(Name alias) {
        return new RoleTable(alias, this);
    }

    @Override
    public RoleTable as(Table<?> alias) {
        return new RoleTable(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public RoleTable rename(String name) {
        return new RoleTable(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public RoleTable rename(Name name) {
        return new RoleTable(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public RoleTable rename(Table<?> name) {
        return new RoleTable(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public RoleTable where(Condition condition) {
        return new RoleTable(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public RoleTable where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public RoleTable where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public RoleTable where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public RoleTable where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public RoleTable where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public RoleTable where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public RoleTable where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public RoleTable whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public RoleTable whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
