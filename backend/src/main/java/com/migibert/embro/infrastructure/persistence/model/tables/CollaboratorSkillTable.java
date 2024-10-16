/*
 * This file is generated by jOOQ.
 */
package com.migibert.embro.infrastructure.persistence.model.tables;


import com.migibert.embro.infrastructure.persistence.model.Keys;
import com.migibert.embro.infrastructure.persistence.model.Public;
import com.migibert.embro.infrastructure.persistence.model.tables.CollaboratorTable.CollaboratorPath;
import com.migibert.embro.infrastructure.persistence.model.tables.OrganizationTable.OrganizationPath;
import com.migibert.embro.infrastructure.persistence.model.tables.SkillTable.SkillPath;
import com.migibert.embro.infrastructure.persistence.model.tables.records.CollaboratorSkillRecord;

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
public class CollaboratorSkillTable extends TableImpl<CollaboratorSkillRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.collaborator_skill</code>
     */
    public static final CollaboratorSkillTable COLLABORATOR_SKILL = new CollaboratorSkillTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CollaboratorSkillRecord> getRecordType() {
        return CollaboratorSkillRecord.class;
    }

    /**
     * The column <code>public.collaborator_skill.organization_id</code>.
     */
    public final TableField<CollaboratorSkillRecord, UUID> ORGANIZATION_ID = createField(DSL.name("organization_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.collaborator_skill.collaborator_id</code>.
     */
    public final TableField<CollaboratorSkillRecord, UUID> COLLABORATOR_ID = createField(DSL.name("collaborator_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.collaborator_skill.skill_id</code>.
     */
    public final TableField<CollaboratorSkillRecord, UUID> SKILL_ID = createField(DSL.name("skill_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.collaborator_skill.proficiency</code>.
     */
    public final TableField<CollaboratorSkillRecord, Integer> PROFICIENCY = createField(DSL.name("proficiency"), SQLDataType.INTEGER.nullable(false), this, "");

    private CollaboratorSkillTable(Name alias, Table<CollaboratorSkillRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private CollaboratorSkillTable(Name alias, Table<CollaboratorSkillRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.collaborator_skill</code> table reference
     */
    public CollaboratorSkillTable(String alias) {
        this(DSL.name(alias), COLLABORATOR_SKILL);
    }

    /**
     * Create an aliased <code>public.collaborator_skill</code> table reference
     */
    public CollaboratorSkillTable(Name alias) {
        this(alias, COLLABORATOR_SKILL);
    }

    /**
     * Create a <code>public.collaborator_skill</code> table reference
     */
    public CollaboratorSkillTable() {
        this(DSL.name("collaborator_skill"), null);
    }

    public <O extends Record> CollaboratorSkillTable(Table<O> path, ForeignKey<O, CollaboratorSkillRecord> childPath, InverseForeignKey<O, CollaboratorSkillRecord> parentPath) {
        super(path, childPath, parentPath, COLLABORATOR_SKILL);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class CollaboratorSkillPath extends CollaboratorSkillTable implements Path<CollaboratorSkillRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> CollaboratorSkillPath(Table<O> path, ForeignKey<O, CollaboratorSkillRecord> childPath, InverseForeignKey<O, CollaboratorSkillRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private CollaboratorSkillPath(Name alias, Table<CollaboratorSkillRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public CollaboratorSkillPath as(String alias) {
            return new CollaboratorSkillPath(DSL.name(alias), this);
        }

        @Override
        public CollaboratorSkillPath as(Name alias) {
            return new CollaboratorSkillPath(alias, this);
        }

        @Override
        public CollaboratorSkillPath as(Table<?> alias) {
            return new CollaboratorSkillPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<CollaboratorSkillRecord> getPrimaryKey() {
        return Keys.COLLABORATOR_SKILL_PKEY;
    }

    @Override
    public List<ForeignKey<CollaboratorSkillRecord, ?>> getReferences() {
        return Arrays.asList(Keys.COLLABORATOR_SKILL__FK_COLLABORATOR_SKILL_ORGANIZATION, Keys.COLLABORATOR_SKILL__FK_COLLABORATOR_SKILL_COLLABORATOR, Keys.COLLABORATOR_SKILL__FK_COLLABORATOR_SKILL_SKILL);
    }

    private transient OrganizationPath _organization;

    /**
     * Get the implicit join path to the <code>public.organization</code> table.
     */
    public OrganizationPath organization() {
        if (_organization == null)
            _organization = new OrganizationPath(this, Keys.COLLABORATOR_SKILL__FK_COLLABORATOR_SKILL_ORGANIZATION, null);

        return _organization;
    }

    private transient CollaboratorPath _collaborator;

    /**
     * Get the implicit join path to the <code>public.collaborator</code> table.
     */
    public CollaboratorPath collaborator() {
        if (_collaborator == null)
            _collaborator = new CollaboratorPath(this, Keys.COLLABORATOR_SKILL__FK_COLLABORATOR_SKILL_COLLABORATOR, null);

        return _collaborator;
    }

    private transient SkillPath _skill;

    /**
     * Get the implicit join path to the <code>public.skill</code> table.
     */
    public SkillPath skill() {
        if (_skill == null)
            _skill = new SkillPath(this, Keys.COLLABORATOR_SKILL__FK_COLLABORATOR_SKILL_SKILL, null);

        return _skill;
    }

    @Override
    public CollaboratorSkillTable as(String alias) {
        return new CollaboratorSkillTable(DSL.name(alias), this);
    }

    @Override
    public CollaboratorSkillTable as(Name alias) {
        return new CollaboratorSkillTable(alias, this);
    }

    @Override
    public CollaboratorSkillTable as(Table<?> alias) {
        return new CollaboratorSkillTable(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public CollaboratorSkillTable rename(String name) {
        return new CollaboratorSkillTable(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public CollaboratorSkillTable rename(Name name) {
        return new CollaboratorSkillTable(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public CollaboratorSkillTable rename(Table<?> name) {
        return new CollaboratorSkillTable(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public CollaboratorSkillTable where(Condition condition) {
        return new CollaboratorSkillTable(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public CollaboratorSkillTable where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public CollaboratorSkillTable where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public CollaboratorSkillTable where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public CollaboratorSkillTable where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public CollaboratorSkillTable where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public CollaboratorSkillTable where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public CollaboratorSkillTable where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public CollaboratorSkillTable whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public CollaboratorSkillTable whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
