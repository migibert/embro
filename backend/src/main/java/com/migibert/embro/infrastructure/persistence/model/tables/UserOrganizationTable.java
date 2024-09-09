/*
 * This file is generated by jOOQ.
 */
package com.migibert.embro.infrastructure.persistence.model.tables;


import com.migibert.embro.infrastructure.persistence.model.Keys;
import com.migibert.embro.infrastructure.persistence.model.Public;
import com.migibert.embro.infrastructure.persistence.model.tables.OrganizationTable.OrganizationPath;
import com.migibert.embro.infrastructure.persistence.model.tables.records.UserOrganizationRecord;

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
public class UserOrganizationTable extends TableImpl<UserOrganizationRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.user_organization</code>
     */
    public static final UserOrganizationTable USER_ORGANIZATION = new UserOrganizationTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserOrganizationRecord> getRecordType() {
        return UserOrganizationRecord.class;
    }

    /**
     * The column <code>public.user_organization.user_id</code>.
     */
    public final TableField<UserOrganizationRecord, String> USER_ID = createField(DSL.name("user_id"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.user_organization.organization_id</code>.
     */
    public final TableField<UserOrganizationRecord, UUID> ORGANIZATION_ID = createField(DSL.name("organization_id"), SQLDataType.UUID.nullable(false), this, "");

    private UserOrganizationTable(Name alias, Table<UserOrganizationRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private UserOrganizationTable(Name alias, Table<UserOrganizationRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.user_organization</code> table reference
     */
    public UserOrganizationTable(String alias) {
        this(DSL.name(alias), USER_ORGANIZATION);
    }

    /**
     * Create an aliased <code>public.user_organization</code> table reference
     */
    public UserOrganizationTable(Name alias) {
        this(alias, USER_ORGANIZATION);
    }

    /**
     * Create a <code>public.user_organization</code> table reference
     */
    public UserOrganizationTable() {
        this(DSL.name("user_organization"), null);
    }

    public <O extends Record> UserOrganizationTable(Table<O> path, ForeignKey<O, UserOrganizationRecord> childPath, InverseForeignKey<O, UserOrganizationRecord> parentPath) {
        super(path, childPath, parentPath, USER_ORGANIZATION);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class UserOrganizationPath extends UserOrganizationTable implements Path<UserOrganizationRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> UserOrganizationPath(Table<O> path, ForeignKey<O, UserOrganizationRecord> childPath, InverseForeignKey<O, UserOrganizationRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private UserOrganizationPath(Name alias, Table<UserOrganizationRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public UserOrganizationPath as(String alias) {
            return new UserOrganizationPath(DSL.name(alias), this);
        }

        @Override
        public UserOrganizationPath as(Name alias) {
            return new UserOrganizationPath(alias, this);
        }

        @Override
        public UserOrganizationPath as(Table<?> alias) {
            return new UserOrganizationPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<UserOrganizationRecord> getPrimaryKey() {
        return Keys.USER_ORGANIZATION_PKEY;
    }

    @Override
    public List<ForeignKey<UserOrganizationRecord, ?>> getReferences() {
        return Arrays.asList(Keys.USER_ORGANIZATION__FK_USER_ORGANIZATION);
    }

    private transient OrganizationPath _organization;

    /**
     * Get the implicit join path to the <code>public.organization</code> table.
     */
    public OrganizationPath organization() {
        if (_organization == null)
            _organization = new OrganizationPath(this, Keys.USER_ORGANIZATION__FK_USER_ORGANIZATION, null);

        return _organization;
    }

    @Override
    public UserOrganizationTable as(String alias) {
        return new UserOrganizationTable(DSL.name(alias), this);
    }

    @Override
    public UserOrganizationTable as(Name alias) {
        return new UserOrganizationTable(alias, this);
    }

    @Override
    public UserOrganizationTable as(Table<?> alias) {
        return new UserOrganizationTable(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserOrganizationTable rename(String name) {
        return new UserOrganizationTable(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserOrganizationTable rename(Name name) {
        return new UserOrganizationTable(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserOrganizationTable rename(Table<?> name) {
        return new UserOrganizationTable(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public UserOrganizationTable where(Condition condition) {
        return new UserOrganizationTable(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public UserOrganizationTable where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public UserOrganizationTable where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public UserOrganizationTable where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public UserOrganizationTable where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public UserOrganizationTable where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public UserOrganizationTable where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public UserOrganizationTable where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public UserOrganizationTable whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public UserOrganizationTable whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
