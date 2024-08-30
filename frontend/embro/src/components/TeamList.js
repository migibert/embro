import { List, ListItem, ListItemText, Typography } from '@mui/material';
import { React, useContext } from 'react';
import { OrganizationContext } from '../context/OrganizationContext';
import { useTeams } from '../hooks/useTeams';

const TeamList = () => {
    const { currentOrganization} = useContext(OrganizationContext);
    const teams = useTeams(currentOrganization?.id);
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
        </div>
      );
    };

export default TeamList;

