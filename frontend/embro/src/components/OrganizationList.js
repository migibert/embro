import { useAuth0 } from '@auth0/auth0-react';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import Delete from '@mui/icons-material/Delete';
import SaveRoundedIcon from '@mui/icons-material/SaveRounded';
import { Box, IconButton, List, ListItem, ListItemText, TextField, Typography } from '@mui/material';
import { React, useContext, useState } from 'react';
import { OrganizationContext } from '../context/OrganizationContext';
import { createOrganization, deleteOrganization } from '../utils/api';


const OrganizationList = () => {
  const { organizations, setOrganizations, currentOrganization, setCurrentOrganization } = useContext(OrganizationContext);
  const { getAccessTokenSilently } = useAuth0();
  const [adding, setAdding] = useState(false);
  const [newName, setNewName] = useState(null);

  const addOrganization = async () => {
    const token = await getAccessTokenSilently();
    const created = await createOrganization(token, newName);
    setOrganizations([...organizations, created]);
    setCurrentOrganization(created);
    setAdding(false);
  }

  const removeOrganization = async (id) => {
    const token = await getAccessTokenSilently();
    await deleteOrganization(token, id);
    const updated = organizations.filter((organization) => organization.id !== id);
    setOrganizations(updated);
    if(currentOrganization.id === id) {
      setCurrentOrganization(updated.length > 0 ? updated[0] : null);
    }
  };

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
      <Box alignItems={'flex-end'}>
        <List sx={{width: '100%'}}>
          {organizations?.map((organization) =>
            <ListItem 
              key={organization.id}
              style={{display: 'flex'}}
              secondaryAction={
                <IconButton edge="end" aria-label="delete" onClick={() => removeOrganization(organization.id)}>
                  <Delete />
                </IconButton>
              }
            >
              <ListItemText primary={organization.name} />
            </ListItem>
          )}
        </List>
      </Box>
    </div>
  );
};

export default OrganizationList;

