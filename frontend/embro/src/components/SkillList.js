import { useAuth0 } from '@auth0/auth0-react';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import SaveRoundedIcon from '@mui/icons-material/SaveRounded';
import { IconButton, List, ListItem, ListItemText, TextField, Typography } from '@mui/material';
import { React, useContext, useEffect, useState } from 'react';
import { OrganizationContext } from '../context/OrganizationContext';
import { createSkill, listSkills } from '../utils/api';

const SkillList = () => {
  const { getAccessTokenSilently } = useAuth0();
  const { currentOrganization} = useContext(OrganizationContext);
  const [skills, setSkills] = useState([]);
  const [adding, setAdding] = useState(false);
  const [newName, setNewName] = useState(null);

  const addSkill = async () => {
    const token = await getAccessTokenSilently();
    const created = await createSkill(token, currentOrganization.id, newName);
    setSkills([...skills, created]);
    setAdding(false);
  };

  useEffect(() => {
    const load = async () => {
      if(!currentOrganization) {
        return;
      }
      const token = await getAccessTokenSilently();
      const loadedSkills = await listSkills(token, currentOrganization.id);
      setSkills(loadedSkills);
    };
    load();
  }, [getAccessTokenSilently, currentOrganization]);

  return (
    <div>
      <Typography variant="h2">Skills</Typography>
      <List dense="true">
        {skills?.map((skill) => 
          <ListItem key={skill.id}>
            <ListItemText primary={skill.name} />
          </ListItem>
        )}
      </List>
      <IconButton onClick={() => setAdding(true)}>
        <AddCircleIcon/>
      </IconButton>
      {adding && 
      <>
        <TextField required id="new-skill-name" label="Name" variant="filled" onChange={(e) => setNewName(e.target.value)}/>
        <IconButton onClick={() => addSkill()}>
          <SaveRoundedIcon/>
        </IconButton>
      </>
      }
    </div>
  );
};

export default SkillList;

