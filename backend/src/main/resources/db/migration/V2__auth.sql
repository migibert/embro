CREATE TYPE ROLE AS ENUM(
    'OWNER',
    'EDITOR',
    'VIEWER'
);

CREATE TABLE USER_ORGANIZATION(
   user_id TEXT NOT NULL,
   organization_id UUID NOT NULL,
   user_email TEXT NOT NULL,
   role ROLE NOT NULL,
   PRIMARY KEY (user_id, organization_id),
   CONSTRAINT fk_user_organization_organization FOREIGN KEY (organization_id) REFERENCES organization(id)
);

CREATE TABLE INVITATION(
    id UUID NOT NULL,
    email VARCHAR(256) NOT NULL,
    invited_by TEXT NOT NULL,
    expires_at TIMESTAMP NOT NULL DEFAULT (NOW() + INTERVAL '7 days'),
    organization_id UUID NOT NULL,
    role ROLE NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_invitation_organization FOREIGN KEY (organization_id) REFERENCES organization(id)
);

CREATE OR REPLACE FUNCTION delete_expired_invitations() RETURNS TRIGGER AS $$
BEGIN
    DELETE FROM INVITATION WHERE expires_at < NOW();
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER TRIGGER_DELETE_EXPIRED_INVITATIONS
AFTER INSERT OR UPDATE ON INVITATION
FOR EACH ROW
EXECUTE FUNCTION delete_expired_invitations();
