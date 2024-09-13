import { useAuth0 } from '@auth0/auth0-react';
import { Box, Button, Grid2 as Grid, Stack, TextField, Typography } from '@mui/material';
import { React, useContext, useEffect, useState } from 'react';
import TeamCard from '../components/TeamCard';
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
      <Grid container spacing={2} columns={12} justifyItems={'flex-start'} width={'fit-content'}>
        {teams?.map((team) => (
          <Grid item xs>
            <TeamCard team={team}/>
          </Grid>
        ))}
      </Grid>
      <Box>
        {!adding && (
          <Box sx={{ mt: 4 }}>
            <Button variant="contained" color="info" onClick={() => setAdding(true)}>
              Add Team
            </Button>
          </Box>
        )}
        {adding && (
        <Box sx={{ mt: 4 }}>
          <TextField required id="new-team-name" label="Name" variant="filled" onChange={(e) => setNewName(e.target.value)}/>
          <Stack direction={"row"} spacing={2} sx={{mt: 2}}>
            <Button variant="contained" color="success" onClick={addTeam}>
              Save
            </Button>
            <Button variant="contained" color="error" onClick={() => setAdding(false)}>
              Cancel
            </Button>
          </Stack>
        </Box>
        )}
      </Box>
    </div>
  );
};

export default Teams;

