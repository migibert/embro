import { useAuth0 } from '@auth0/auth0-react';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import SaveRoundedIcon from '@mui/icons-material/SaveRounded';
import { IconButton, List, ListItem, ListItemText, TextField, Typography } from '@mui/material';
import { React, useContext, useEffect, useState } from 'react';
import { OrganizationContext } from '../context/OrganizationContext';
import { createTeam, listTeams } from '../utils/api';

const Teams = () => {
  const { getAccessTokenSilently } = useAuth0();
  const { currentOrganization} = useContext(OrganizationContext);
  const [teams, setTeams] = useState([]);
  const [adding, setAdding] = useState(false);
  const [newName, setNewName] = useState(null);

  const addTeam = async () => {
    const token = await getAccessTokenSilently();
    const created = await createTeam(token, currentOrganization.id, newName);
    setTeams([...teams, created]);
    setAdding(false);
  };

  useEffect(() => {
    const load = async () => {
      if(!currentOrganization) {
        return;
      }
      const token = await getAccessTokenSilently();
      const loadedTeams = await listTeams(token, currentOrganization.id);
      setTeams(loadedTeams);
    };
    load();
  }, [getAccessTokenSilently, currentOrganization]);

  return (
    <div>
      <Typography variant="h1">Teams</Typography>
      <List>
        {teams?.map((team) => 
          <ListItem key={team.id}>
            <ListItemText primary={team.name} />
          </ListItem>
        )}
      </List>
      <IconButton onClick={() => setAdding(true)}>
        <AddCircleIcon/>
      </IconButton>
      {adding && 
      <>
        <TextField required id="new-team-name" label="Name" variant="filled" onChange={(e) => setNewName(e.target.value)}/>
        <IconButton onClick={() => addTeam()}>
          <SaveRoundedIcon/>
        </IconButton>
      </>
      }
    </div>
  );
};

export default Teams;

