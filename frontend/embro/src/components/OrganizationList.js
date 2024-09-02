import { useAuth0 } from '@auth0/auth0-react';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import SaveRoundedIcon from '@mui/icons-material/SaveRounded';
import { IconButton, List, ListItem, ListItemText, TextField, Typography } from '@mui/material';
import { React, useContext, useState } from 'react';
import { OrganizationContext } from '../context/OrganizationContext';
import { createOrganization } from '../utils/api';

const OrganizationList = () => {
  const { organizations, setOrganizations } = useContext(OrganizationContext);
  const { getAccessTokenSilently } = useAuth0();
  const [adding, setAdding] = useState(false);
  const [newName, setNewName] = useState(null);

  const addOrganization = async () => {
    const token = await getAccessTokenSilently();
    const body = JSON.stringify({id: null, name: newName});
    const created = await createOrganization(token, body);
    setOrganizations([...organizations, created]);
    setAdding(false);
  }

  return (
    <div>
      <Typography variant="h2">Organizations</Typography>
      <IconButton onClick={() => setAdding(true)}>
        <AddCircleIcon/>
      </IconButton>
      {adding && 
      <>
        <TextField required id="new-organization-name" label="Name" variant="filled" onChange={(e) => setNewName(e.target.value)}/>
        <IconButton onClick={() => addOrganization()}>
          <SaveRoundedIcon/>
        </IconButton>
      </>
      }

      <List>
        {organizations?.map((organization) => 
          <ListItem key={organization.id}>
            <ListItemText primary={organization.name} />
          </ListItem>
        )}
      </List>
    </div>
  );
};

export default OrganizationList;

