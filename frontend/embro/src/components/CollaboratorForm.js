import { useAuth0 } from "@auth0/auth0-react";
import { Delete } from "@mui/icons-material";
import { Box, Button, List, ListItem, ListItemIcon, ListItemText, ListSubheader, MenuItem, Rating, Select, Stack, TextField, Typography } from "@mui/material";
import { DatePicker } from "@mui/x-date-pickers";
import { useContext, useEffect, useState } from "react";
import { OrganizationContext } from "../context/OrganizationContext";
import { createCollaborator, listSeniorities, listSkills } from "../utils/api";

function CollaboratorForm({ onSubmitted, onCancelled }) {
  const { getAccessTokenSilently } = useAuth0();
  const { currentOrganization} = useContext(OrganizationContext);

  const [ email, setEmail ] = useState(null);
  const [ firstname, setFirstname ] = useState(null);
  const [ lastname, setLastname ] = useState(null);
  const [ role, setRole ] = useState(null);
  const [ birthDate, setBirthDate ] = useState(null);
  const [ startDate, setStartDate ] = useState(null);
  const [ seniority, setSeniority ] = useState(null);
  const [ skillLevels, setSkillLevels ] = useState([]);
  const [ availableSkills, setAvailableSkills] = useState([]);
  const [ availableSeniorities, setAvailableSeniorities] = useState([]);
  const [ availableRoles, setAvailableRoles] = useState([]);

  useEffect(() => {
    const load = async () => {
      if(!currentOrganization) {
        return;
      }
      const token = await getAccessTokenSilently();
      const loadedSkills = await listSkills(token, currentOrganization.id);
      setAvailableSkills(loadedSkills);
      //const loadedRoles = await listSkills(token, currentOrganization.id);
      //setAvailableRoles(loadedRoles);
      const loadedSeniorities = await listSeniorities(token, currentOrganization.id);
      setAvailableSeniorities(loadedSeniorities);
    }
    load();
  }, [currentOrganization, getAccessTokenSilently]);

  const addCollaborator = async () => {
    const toCreate = {
      id: null,
      email: email,
      firstname: firstname,
      lastname: lastname,
      role: role,
      birthDate: birthDate,
      startDate: startDate,
      seniority: seniority,
      skills: skillLevels,
    }

    const token = await getAccessTokenSilently();
    const created = await createCollaborator(token, currentOrganization.id, toCreate);
    onSubmitted(created);
  };

  return (
    <>
      <Typography variant="h2" sx={{ marginBottom: 2 }} columns>
        New collaborator
      </Typography>
      <Stack direction={"row"} spacing={5} sx={{marginBottom: 2}}>
        <Box component="fieldset" sx={{ width: '50%'}}>
          <legend>Personal Information</legend>
          <Stack sx={{ m: 2 }} direction={"row"} spacing={3}>
            <TextField
              required
              label="First Name"
              value={firstname}
              onChange={(e) => setFirstname(e.target.value)}
            />
            <TextField
              required
              label="Last Name"
              value={lastname}
              onChange={(e) => setLastname(e.target.value)}
            />
            <TextField
              required
              label="Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </Stack>
          <Stack sx={{ m: 2 }} direction={"row"} spacing={3}>
            <Select
              sx={{ width: "50%" }}
              required
              value={seniority}
              label="Seniority"
              onChange={(e) => setSeniority(e.target.value)}
            >
              {availableSeniorities?.map((seniority) => (
                <MenuItem key={seniority.name} value={seniority.name}>
                  {seniority.name}
                </MenuItem>
              ))}
            </Select>
            <Select
              sx={{ width: "50%" }}
              required
              value={role}
              label="Role"
              onChange={(e) => setRole(e.target.value)}
            >
              <MenuItem key="test" value="test">
                Engineering Manager
              </MenuItem>
            </Select>
          </Stack>
          <Stack sx={{ m: 2 }} direction={"row"} spacing={3}>
            <DatePicker
              sx={{ width: "50%" }}
              label="Birth Date"
              value={birthDate}
              onChange={(newValue) => setBirthDate(newValue)}
            />
            <DatePicker
              sx={{ width: "50%" }}
              label="Start Date"
              value={startDate}
              onChange={(newValue) => setStartDate(newValue)}
            />
          </Stack>
        </Box>
        <Box component="fieldset" sx={{ width: '50%', flexWrap: 'wrap'}}>
          <legend>Skills</legend>
          <Stack sx={{ m: 2 }} direction={"row"} spacing={3}>
            <List 
              sx={{border:1, width: "50%"}}
              subheader={
                <ListSubheader component="div">Available Skills</ListSubheader>
              }
            >
              {availableSkills?.map((skill) => (
                <ListItem key={skill.id} button onClick={() => {
                    setSkillLevels([...skillLevels, { skill: skill, level: 0 }]);
                    setAvailableSkills(availableSkills.filter(s => s.id !== skill.id));
                  }}>
                  <ListItemText primary={skill.name} />
                </ListItem>
              ))}
            </List>
            <List 
              sx={{border:1, width: "50%"}}
              subheader={
                <ListSubheader component="div">Assigned Skills</ListSubheader>
              }
            >
              {skillLevels?.map((skillLevel) => (
                <ListItem key={skillLevel.skill.id}>
                  <ListItemText primary={skillLevel.skill.name} />
                  <Rating
                    name={skillLevel.skill.id}
                    value={skillLevel.level}
                    onChange={(_, newValue) => {
                      skillLevel.level = newValue;
                      setSkillLevels([...skillLevels]);
                    }}
                  />
                  <ListItemIcon>
                    <Delete onClick={() => {
                      setSkillLevels(skillLevels.filter(s => s.skill.id !== skillLevel.skill.id));
                      setAvailableSkills([...availableSkills, skillLevel.skill]);
                    }}/>
                  </ListItemIcon>
                </ListItem>
              ))}
            </List>
          </Stack>
        </Box>
      </Stack>
      <Stack direction={"row"} spacing={2}>
        <Button variant="contained" color="success" onClick={addCollaborator}>
          Save
        </Button>
        <Button variant="outlined" color="error" onClick={onCancelled}>
          Cancel
        </Button>
      </Stack>
    </>
  )
}

export default CollaboratorForm;