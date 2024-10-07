import { useAuth0 } from '@auth0/auth0-react';
import { Add } from '@mui/icons-material';
import { Box, Card, CardActionArea, Stack, Typography } from '@mui/material';
import { React, useContext, useEffect, useState } from 'react';
import { OrganizationContext } from '../context/OrganizationContext';
import { UserContext } from '../context/UserContext';
import { createTeam, deleteTeam, listTeams, updateTeam } from '../utils/api';
import TeamCard from './TeamCard';

const Teams = () => {
  const { getAccessTokenSilently } = useAuth0();
  const { currentOrganization} = useContext(OrganizationContext);
  const { isAllowedToEdit } = useContext(UserContext);
  const [teams, setTeams] = useState([]);
  const [adding, setAdding] = useState(false);

  const defaultTeam = {
    id: null,
    name: null,
  };

  const saveTeam = async (team) => {
    const token = await getAccessTokenSilently();
    let saved = team;
    if(team.id) {
      saved = await updateTeam(token, currentOrganization.id, team);
    } else {
      saved = await createTeam(token, currentOrganization.id, team);
      setTeams([...teams, saved].filter(t => t.id !== null));
      setAdding(false);
    }
  };

  const removeTeam = async (team) => {
    setTeams(teams.filter(t => t.id !== team.id));
    if(team.id) {
      const token = await getAccessTokenSilently();
      await deleteTeam(token, currentOrganization.id, team.id);
    }
  }

  const addTeam = async () => {
    setAdding(true);
  }

  const cancelCreation = () => {
    setAdding(false);
  }

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
    <Stack spacing={4} alignItems={'baseline'}>
      <Typography variant="h1">Teams</Typography>
      <Box display={'flex'} flexWrap={'wrap'} alignItems={'stretch'} gap={2}>
        {adding ? 
          <TeamCard 
            key="new-team" 
            team={defaultTeam} 
            onDelete={removeTeam} 
            onSave={saveTeam} 
            onCancel={cancelCreation} 
            disabled={!isAllowedToEdit(currentOrganization.id)}
          />
          :
          <Card key="add-team" sx={{ width: 250, height: 200, textAlign: 'center' }}>
            <CardActionArea
              disabled={!isAllowedToEdit(currentOrganization?.id)}
              onClick={addTeam}
              sx={{ width: '100%', height: '100%'}}
            >
              <Add sx={{ fontSize: 20 }}/>
            </CardActionArea>
          </Card>
        }
        {teams?.map((team) => (
          <TeamCard 
            key={team.id}
            team={team}
            onDelete={removeTeam}
            onSave={saveTeam}
            disabled={!isAllowedToEdit(currentOrganization.id)}
          />
        ))}
      </Box>
    </Stack>
  );
};

export default Teams;

