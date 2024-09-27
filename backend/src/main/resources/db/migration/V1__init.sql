CREATE TABLE ORGANIZATION(
    id UUID,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE SENIORITY(
   id UUID,
   organization_id UUID NOT NULL,
   name VARCHAR(100) NOT NULL,
   PRIMARY KEY (id),
   CONSTRAINT fk_seniority_organization FOREIGN KEY (organization_id) REFERENCES organization(id)
);

CREATE TABLE SKILL(
    id UUID,
    organization_id UUID NOT NULL,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_skill_organization FOREIGN KEY (organization_id) REFERENCES organization(id)
);

CREATE TABLE ROLE(
    id UUID,
    organization_id UUID NOT NULL,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_role_organization FOREIGN KEY (organization_id) REFERENCES organization(id)
);

CREATE TABLE COLLABORATOR(
    id UUID,
    organization_id UUID NOT NULL,
    email VARCHAR (256) NOT NULL,
    firstname VARCHAR(256) NOT NULL,
    lastname VARCHAR(256) NOT NULL,
    role VARCHAR(100) NOT NULL,
    birth_date DATE,
    start_date DATE,
    seniority_name VARCHAR(100),
    CONSTRAINT fk_collaborator_organization FOREIGN KEY (organization_id) REFERENCES organization(id),
    CONSTRAINT unique_email_organization UNIQUE (email, organization_id),
    PRIMARY KEY (id)
);

CREATE TABLE COLLABORATOR_SKILL(
    organization_id UUID NOT NULL,
    collaborator_id UUID NOT NULL,
    skill_id UUID NOT NULL,
    proficiency INTEGER NOT NULL,
    PRIMARY KEY (collaborator_id, skill_id),
    CONSTRAINT fk_collaborator_skill_organization FOREIGN KEY (organization_id) REFERENCES organization(id),
    CONSTRAINT fk_collaborator_skill_collaborator FOREIGN KEY (collaborator_id) REFERENCES collaborator(id),
    CONSTRAINT fk_collaborator_skill_skill FOREIGN KEY (skill_id) REFERENCES skill(id)
);

CREATE TABLE TEAM(
    id UUID,
    organization_id UUID NOT NULL,
    name VARCHAR(100) NOT NULL,
    mission VARCHAR(500),
    email VARCHAR(250),
    instant_message VARCHAR(250),
    phone VARCHAR(15),
    PRIMARY KEY (id),
    CONSTRAINT fk_team_organization FOREIGN KEY (organization_id) REFERENCES organization(id)
);


CREATE TABLE TEAM_COLLABORATOR(
    organization_id UUID NOT NULL,
    team_id UUID NOT NULL,
    collaborator_id UUID NOT NULL,
    key_player boolean NOT NULL DEFAULT false,
    PRIMARY KEY (team_id, collaborator_id),
    CONSTRAINT fk_team_collaborator_organization FOREIGN KEY (organization_id) REFERENCES organization(id),
    CONSTRAINT fk_team_collaborator_collaborator FOREIGN KEY (collaborator_id) REFERENCES collaborator(id),
    CONSTRAINT fk_team_collaborator_team FOREIGN KEY (team_id) REFERENCES team(id)
);
