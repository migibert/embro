import { useAuth0 } from '@auth0/auth0-react';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import SaveRoundedIcon from '@mui/icons-material/SaveRounded';
import { IconButton, List, ListItem, ListItemText, TextField, Typography } from '@mui/material';
import { React, useContext, useEffect, useState } from 'react';
import { OrganizationContext } from '../context/OrganizationContext';
import { createSeniority, listSeniorities } from '../utils/api';

const SeniorityList = () => {
  const { getAccessTokenSilently } = useAuth0();
  const { currentOrganization} = useContext(OrganizationContext);
  const [seniorities, setSeniorities] = useState([]);
  const [adding, setAdding] = useState(false);
  const [newName, setNewName] = useState(null);

  const addSeniority = async () => {
    const token = await getAccessTokenSilently();
    const created = await createSeniority(token, currentOrganization.id, newName);
    setSeniorities([...seniorities, created]);
    setAdding(false);
  };

  useEffect(() => {
    const load = async () => {
      if(!currentOrganization) {
        return;
      }
      const token = await getAccessTokenSilently();
      const loadedSeniorities = await listSeniorities(token, currentOrganization.id);
      setSeniorities(loadedSeniorities);
    };
    load();
  }, [getAccessTokenSilently, currentOrganization]);

  return (
    <div>
      <Typography variant="h2">Seniorities</Typography>
      <List dense="true">
        {seniorities?.map((seniority) => 
          <ListItem key={seniority.id}>
            <ListItemText primary={seniority.name} />
          </ListItem>
        )}
      </List>
      <IconButton onClick={() => setAdding(true)}>
        <AddCircleIcon/>
      </IconButton>
      {adding && 
      <>
        <TextField required id="new-Seniority-name" label="Name" variant="filled" onChange={(e) => setNewName(e.target.value)}/>
        <IconButton onClick={() => addSeniority()}>
          <SaveRoundedIcon/>
        </IconButton>
      </>
      }
    </div>
  );
};

export default SeniorityList;

