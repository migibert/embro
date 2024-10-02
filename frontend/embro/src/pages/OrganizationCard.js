import { useAuth0 } from "@auth0/auth0-react";
import { Cancel, Delete, PersonAdd, Save } from "@mui/icons-material";
import { Card, CardActions, CardHeader, IconButton, TextField } from "@mui/material";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

function OrganizationCard({organization, onDelete, onSave, onCancel}) {
  const [editMode, setEditMode] = useState(false);
  const [name, setName] = useState(organization?.name);
  const { getAccessTokenSilently } = useAuth0();

  const save = () => {
    setEditMode(false);
    const organizationToSave = {id: organization?.id, name: name};
    onSave(organizationToSave);
  };

  const cancel = () => {
    setEditMode(false);
    if(onCancel) {
      onCancel();
    }
  }

  useEffect(() => {
    const load = async () => {
      if(!organization?.id) {
        setEditMode(true);
        return;
      }
      setName(organization?.name);
    }
    load();
  }, [organization, getAccessTokenSilently])

  return (
    <Card sx={{
      width: 250,
      height: 200,
      justifyContent: 'space-between',
      textAlign: 'center',
      backgroundColor: `hsl(${Math.random()*360}, 25%, 90%)` ,
      display: 'flex',
      flexDirection: 'column',
    }}>
      <CardHeader 
        title={editMode === false ? organization?.name : <TextField size="large" value={name} onChange={(e) => setName(e.target.value)} />}
        titleTypographyProps={{ textOverflow: 'ellipsis', overflow: 'hidden', whiteSpace: 'wrap', fontSize: '1.3em' }}
      />
      <CardActions sx={{ justifyContent: 'space-between'}}>
        {editMode && <>
          <IconButton onClick={() => save()}>
            <Save />
          </IconButton>
          <IconButton onClick={() => cancel()}>
            <Cancel />
          </IconButton>
        </>
        }
        {!editMode && <>
          <Link to={`/invitations?organizationId=${organization?.id}`}>
            <IconButton disabled={!organization?.id}>
              <PersonAdd />
            </IconButton>
          </Link>
          <IconButton disabled={!organization?.id} onClick={() => onDelete(organization)}>
            <Delete/>
          </IconButton>
          </>
      }
      </CardActions>
    </Card>
  );
}

export default OrganizationCard;