import { useAuth0 } from "@auth0/auth0-react";
import { Cancel, Delete, Info, Save } from "@mui/icons-material";
import { Card, CardActions, CardHeader, IconButton, TextField } from "@mui/material";
import { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { OrganizationContext } from "../context/OrganizationContext";

function TeamCard({team, onDelete, onSave, onCancel, disabled}) {
  const { currentOrganization } = useContext(OrganizationContext);
  const [editMode, setEditMode] = useState(false);
  const [name, setName] = useState(team.name);
  const { getAccessTokenSilently } = useAuth0();

  const save = () => {
    setEditMode(false);
    const teamToSave = {id: team.id, name: name};
    onSave(teamToSave);
  };

  const cancel = () => {
    setEditMode(false);
    if(onCancel) {
      onCancel();
    }
  }

  useEffect(() => {
    const load = async () => {
      if(!team?.id) {
        setEditMode(true);
        return;
      }
      setName(team.name);
    }
    load();
  }, [team, currentOrganization, getAccessTokenSilently])

  if(team === null) {
    return <div/>;
  }

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
        title={editMode === false ? team.name : <TextField disabled={disabled} size="small" value={name} onChange={(e) => setName(e.target.value)} />}
        titleTypographyProps={{ textOverflow: 'ellipsis', overflow: 'hidden', whiteSpace: 'wrap', fontSize: '1.3em' }}
      />
      <CardActions sx={{ justifyContent: 'space-between'}}>
        {editMode && <>
          <IconButton disabled={disabled} onClick={() => save()}>
            <Save />
          </IconButton>
          <IconButton disabled={disabled} onClick={() => cancel()}>
            <Cancel />
          </IconButton>
        </>}
        {!editMode && <>
          <Link to={`/teams/${team?.id}`}>
            <IconButton disabled={!team?.id}>
              <Info/>
            </IconButton>
          </Link>
          <IconButton disabled={!team?.id || disabled} onClick={() => onDelete(team)}>
            <Delete/>
          </IconButton>
        </>}
      </CardActions>
    </Card>
  );
}

export default TeamCard;