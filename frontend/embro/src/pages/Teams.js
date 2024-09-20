import { useAuth0 } from '@auth0/auth0-react';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import { IconButton, Stack, Typography } from '@mui/material';
import { React, useContext, useEffect, useState } from 'react';
import { OrganizationContext } from '../context/OrganizationContext';
import { createTeam, deleteTeam, listTeams } from '../utils/api';
import TeamDetails from './TeamDetails';
import TeamList from './TeamList';
const Teams = () => {
  const { getAccessTokenSilently } = useAuth0();
  const { currentOrganization} = useContext(OrganizationContext);
  const [teams, setTeams] = useState([]);
  const [adding, setAdding] = useState(false);
  const [selected, setSelected] = useState(null);
  const showDetails = adding || selected;

  const saveTeam = async (team) => {
    const token = await getAccessTokenSilently();
    const created = await createTeam(token, currentOrganization.id, team);
    setTeams([...teams, created]);
    setAdding(false);
    setSelected(null);
  };

  const removeTeam = async (team) => {
    console.log("team to remove: " + team);
    const token = await getAccessTokenSilently();
    await deleteTeam(token, currentOrganization.id, team.id);
    setTeams(teams.filter(t => t.id !== team.id));
    setSelected(null);
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
      <TeamList 
        teams={teams}
        onSelect={(team) => setSelected(team)}
        onDelete={(team) => {
          setAdding(false);
          setSelected(null);
          removeTeam(team);
        }}
      />
      {
        showDetails ? (
        <TeamDetails 
          team={selected}
          onSave={(submitted) => {
            setAdding(false);
            setSelected(null);
            saveTeam(submitted)
          }}
          onCancel={() => {
            setAdding(false);
            setSelected(null);
          }}
        />
        ) : (
        <IconButton onClick={() => setAdding(true)}>
          <AddCircleIcon/>
        </IconButton>
        )
      }
    </Stack>
  );
};

export default Teams;

