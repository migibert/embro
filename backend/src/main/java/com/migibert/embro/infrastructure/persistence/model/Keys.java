/*
 * This file is generated by jOOQ.
 */
package com.migibert.embro.infrastructure.persistence.model;


import com.migibert.embro.infrastructure.persistence.model.tables.CollaboratorSkillTable;
import com.migibert.embro.infrastructure.persistence.model.tables.CollaboratorTable;
import com.migibert.embro.infrastructure.persistence.model.tables.FlywaySchemaHistoryTable;
import com.migibert.embro.infrastructure.persistence.model.tables.InvitationTable;
import com.migibert.embro.infrastructure.persistence.model.tables.OrganizationTable;
import com.migibert.embro.infrastructure.persistence.model.tables.PositionTable;
import com.migibert.embro.infrastructure.persistence.model.tables.SeniorityTable;
import com.migibert.embro.infrastructure.persistence.model.tables.SkillTable;
import com.migibert.embro.infrastructure.persistence.model.tables.TeamCollaboratorTable;
import com.migibert.embro.infrastructure.persistence.model.tables.TeamTable;
import com.migibert.embro.infrastructure.persistence.model.tables.UserOrganizationTable;
import com.migibert.embro.infrastructure.persistence.model.tables.records.CollaboratorRecord;
import com.migibert.embro.infrastructure.persistence.model.tables.records.CollaboratorSkillRecord;
import com.migibert.embro.infrastructure.persistence.model.tables.records.FlywaySchemaHistoryRecord;
import com.migibert.embro.infrastructure.persistence.model.tables.records.InvitationRecord;
import com.migibert.embro.infrastructure.persistence.model.tables.records.OrganizationRecord;
import com.migibert.embro.infrastructure.persistence.model.tables.records.PositionRecord;
import com.migibert.embro.infrastructure.persistence.model.tables.records.SeniorityRecord;
import com.migibert.embro.infrastructure.persistence.model.tables.records.SkillRecord;
import com.migibert.embro.infrastructure.persistence.model.tables.records.TeamCollaboratorRecord;
import com.migibert.embro.infrastructure.persistence.model.tables.records.TeamRecord;
import com.migibert.embro.infrastructure.persistence.model.tables.records.UserOrganizationRecord;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<CollaboratorRecord> COLLABORATOR_PKEY = Internal.createUniqueKey(CollaboratorTable.COLLABORATOR, DSL.name("collaborator_pkey"), new TableField[] { CollaboratorTable.COLLABORATOR.ID }, true);
    public static final UniqueKey<CollaboratorRecord> UNIQUE_EMAIL_ORGANIZATION = Internal.createUniqueKey(CollaboratorTable.COLLABORATOR, DSL.name("unique_email_organization"), new TableField[] { CollaboratorTable.COLLABORATOR.EMAIL, CollaboratorTable.COLLABORATOR.ORGANIZATION_ID }, true);
    public static final UniqueKey<CollaboratorSkillRecord> COLLABORATOR_SKILL_PKEY = Internal.createUniqueKey(CollaboratorSkillTable.COLLABORATOR_SKILL, DSL.name("collaborator_skill_pkey"), new TableField[] { CollaboratorSkillTable.COLLABORATOR_SKILL.COLLABORATOR_ID, CollaboratorSkillTable.COLLABORATOR_SKILL.SKILL_ID }, true);
    public static final UniqueKey<FlywaySchemaHistoryRecord> FLYWAY_SCHEMA_HISTORY_PK = Internal.createUniqueKey(FlywaySchemaHistoryTable.FLYWAY_SCHEMA_HISTORY, DSL.name("flyway_schema_history_pk"), new TableField[] { FlywaySchemaHistoryTable.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK }, true);
    public static final UniqueKey<InvitationRecord> INVITATION_PKEY = Internal.createUniqueKey(InvitationTable.INVITATION, DSL.name("invitation_pkey"), new TableField[] { InvitationTable.INVITATION.ID }, true);
    public static final UniqueKey<OrganizationRecord> ORGANIZATION_PKEY = Internal.createUniqueKey(OrganizationTable.ORGANIZATION, DSL.name("organization_pkey"), new TableField[] { OrganizationTable.ORGANIZATION.ID }, true);
    public static final UniqueKey<PositionRecord> POSITION_PKEY = Internal.createUniqueKey(PositionTable.POSITION, DSL.name("position_pkey"), new TableField[] { PositionTable.POSITION.ID }, true);
    public static final UniqueKey<SeniorityRecord> SENIORITY_PKEY = Internal.createUniqueKey(SeniorityTable.SENIORITY, DSL.name("seniority_pkey"), new TableField[] { SeniorityTable.SENIORITY.ID }, true);
    public static final UniqueKey<SkillRecord> SKILL_PKEY = Internal.createUniqueKey(SkillTable.SKILL, DSL.name("skill_pkey"), new TableField[] { SkillTable.SKILL.ID }, true);
    public static final UniqueKey<TeamRecord> TEAM_PKEY = Internal.createUniqueKey(TeamTable.TEAM, DSL.name("team_pkey"), new TableField[] { TeamTable.TEAM.ID }, true);
    public static final UniqueKey<TeamCollaboratorRecord> TEAM_COLLABORATOR_PKEY = Internal.createUniqueKey(TeamCollaboratorTable.TEAM_COLLABORATOR, DSL.name("team_collaborator_pkey"), new TableField[] { TeamCollaboratorTable.TEAM_COLLABORATOR.TEAM_ID, TeamCollaboratorTable.TEAM_COLLABORATOR.COLLABORATOR_ID }, true);
    public static final UniqueKey<UserOrganizationRecord> USER_ORGANIZATION_PKEY = Internal.createUniqueKey(UserOrganizationTable.USER_ORGANIZATION, DSL.name("user_organization_pkey"), new TableField[] { UserOrganizationTable.USER_ORGANIZATION.USER_ID, UserOrganizationTable.USER_ORGANIZATION.ORGANIZATION_ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<CollaboratorRecord, OrganizationRecord> COLLABORATOR__FK_COLLABORATOR_ORGANIZATION = Internal.createForeignKey(CollaboratorTable.COLLABORATOR, DSL.name("fk_collaborator_organization"), new TableField[] { CollaboratorTable.COLLABORATOR.ORGANIZATION_ID }, Keys.ORGANIZATION_PKEY, new TableField[] { OrganizationTable.ORGANIZATION.ID }, true);
    public static final ForeignKey<CollaboratorSkillRecord, CollaboratorRecord> COLLABORATOR_SKILL__FK_COLLABORATOR_SKILL_COLLABORATOR = Internal.createForeignKey(CollaboratorSkillTable.COLLABORATOR_SKILL, DSL.name("fk_collaborator_skill_collaborator"), new TableField[] { CollaboratorSkillTable.COLLABORATOR_SKILL.COLLABORATOR_ID }, Keys.COLLABORATOR_PKEY, new TableField[] { CollaboratorTable.COLLABORATOR.ID }, true);
    public static final ForeignKey<CollaboratorSkillRecord, OrganizationRecord> COLLABORATOR_SKILL__FK_COLLABORATOR_SKILL_ORGANIZATION = Internal.createForeignKey(CollaboratorSkillTable.COLLABORATOR_SKILL, DSL.name("fk_collaborator_skill_organization"), new TableField[] { CollaboratorSkillTable.COLLABORATOR_SKILL.ORGANIZATION_ID }, Keys.ORGANIZATION_PKEY, new TableField[] { OrganizationTable.ORGANIZATION.ID }, true);
    public static final ForeignKey<CollaboratorSkillRecord, SkillRecord> COLLABORATOR_SKILL__FK_COLLABORATOR_SKILL_SKILL = Internal.createForeignKey(CollaboratorSkillTable.COLLABORATOR_SKILL, DSL.name("fk_collaborator_skill_skill"), new TableField[] { CollaboratorSkillTable.COLLABORATOR_SKILL.SKILL_ID }, Keys.SKILL_PKEY, new TableField[] { SkillTable.SKILL.ID }, true);
    public static final ForeignKey<InvitationRecord, OrganizationRecord> INVITATION__FK_INVITATION_ORGANIZATION = Internal.createForeignKey(InvitationTable.INVITATION, DSL.name("fk_invitation_organization"), new TableField[] { InvitationTable.INVITATION.ORGANIZATION_ID }, Keys.ORGANIZATION_PKEY, new TableField[] { OrganizationTable.ORGANIZATION.ID }, true);
    public static final ForeignKey<PositionRecord, OrganizationRecord> POSITION__FK_POSITION_ORGANIZATION = Internal.createForeignKey(PositionTable.POSITION, DSL.name("fk_position_organization"), new TableField[] { PositionTable.POSITION.ORGANIZATION_ID }, Keys.ORGANIZATION_PKEY, new TableField[] { OrganizationTable.ORGANIZATION.ID }, true);
    public static final ForeignKey<SeniorityRecord, OrganizationRecord> SENIORITY__FK_SENIORITY_ORGANIZATION = Internal.createForeignKey(SeniorityTable.SENIORITY, DSL.name("fk_seniority_organization"), new TableField[] { SeniorityTable.SENIORITY.ORGANIZATION_ID }, Keys.ORGANIZATION_PKEY, new TableField[] { OrganizationTable.ORGANIZATION.ID }, true);
    public static final ForeignKey<SkillRecord, OrganizationRecord> SKILL__FK_SKILL_ORGANIZATION = Internal.createForeignKey(SkillTable.SKILL, DSL.name("fk_skill_organization"), new TableField[] { SkillTable.SKILL.ORGANIZATION_ID }, Keys.ORGANIZATION_PKEY, new TableField[] { OrganizationTable.ORGANIZATION.ID }, true);
    public static final ForeignKey<TeamRecord, OrganizationRecord> TEAM__FK_TEAM_ORGANIZATION = Internal.createForeignKey(TeamTable.TEAM, DSL.name("fk_team_organization"), new TableField[] { TeamTable.TEAM.ORGANIZATION_ID }, Keys.ORGANIZATION_PKEY, new TableField[] { OrganizationTable.ORGANIZATION.ID }, true);
    public static final ForeignKey<TeamCollaboratorRecord, CollaboratorRecord> TEAM_COLLABORATOR__FK_TEAM_COLLABORATOR_COLLABORATOR = Internal.createForeignKey(TeamCollaboratorTable.TEAM_COLLABORATOR, DSL.name("fk_team_collaborator_collaborator"), new TableField[] { TeamCollaboratorTable.TEAM_COLLABORATOR.COLLABORATOR_ID }, Keys.COLLABORATOR_PKEY, new TableField[] { CollaboratorTable.COLLABORATOR.ID }, true);
    public static final ForeignKey<TeamCollaboratorRecord, OrganizationRecord> TEAM_COLLABORATOR__FK_TEAM_COLLABORATOR_ORGANIZATION = Internal.createForeignKey(TeamCollaboratorTable.TEAM_COLLABORATOR, DSL.name("fk_team_collaborator_organization"), new TableField[] { TeamCollaboratorTable.TEAM_COLLABORATOR.ORGANIZATION_ID }, Keys.ORGANIZATION_PKEY, new TableField[] { OrganizationTable.ORGANIZATION.ID }, true);
    public static final ForeignKey<TeamCollaboratorRecord, TeamRecord> TEAM_COLLABORATOR__FK_TEAM_COLLABORATOR_TEAM = Internal.createForeignKey(TeamCollaboratorTable.TEAM_COLLABORATOR, DSL.name("fk_team_collaborator_team"), new TableField[] { TeamCollaboratorTable.TEAM_COLLABORATOR.TEAM_ID }, Keys.TEAM_PKEY, new TableField[] { TeamTable.TEAM.ID }, true);
    public static final ForeignKey<UserOrganizationRecord, OrganizationRecord> USER_ORGANIZATION__FK_USER_ORGANIZATION_ORGANIZATION = Internal.createForeignKey(UserOrganizationTable.USER_ORGANIZATION, DSL.name("fk_user_organization_organization"), new TableField[] { UserOrganizationTable.USER_ORGANIZATION.ORGANIZATION_ID }, Keys.ORGANIZATION_PKEY, new TableField[] { OrganizationTable.ORGANIZATION.ID }, true);
}
