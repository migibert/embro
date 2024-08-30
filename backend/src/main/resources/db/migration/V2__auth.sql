CREATE TABLE USER_ORGANIZATION(
   user_id text NOT NULL,
   organization_id UUID NOT NULL,
   PRIMARY KEY (user_id, organization_id),
   CONSTRAINT fk_user_organization FOREIGN KEY (organization_id) REFERENCES organization(id)
);