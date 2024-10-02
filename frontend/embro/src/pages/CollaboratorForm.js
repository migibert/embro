import { useAuth0 } from "@auth0/auth0-react";
import { Delete } from "@mui/icons-material";
import { Box, Button, List, ListItem, ListItemIcon, ListItemText, ListSubheader, MenuItem, Rating, Select, Stack, TextField, Typography } from "@mui/material";
import { DatePicker } from "@mui/x-date-pickers";
import dayjs from "dayjs";
import utc from "dayjs/plugin/utc";

import { useContext, useEffect, useState } from "react";
import { OrganizationContext } from "../context/OrganizationContext";
import { listPositions, listSeniorities, listSkills } from "../utils/api";

function CollaboratorForm({ collaborator, onSave, onCancel }) {
  const { getAccessTokenSilently } = useAuth0();
  const { currentOrganization} = useContext(OrganizationContext);

  const [ email, setEmail ] = useState(collaborator?.email);
  const [ firstname, setFirstname ] = useState(collaborator?.firstname);
  const [ lastname, setLastname ] = useState(collaborator?.lastname);
  const [ position, setPosition ] = useState(collaborator?.position);
  const [ birthDate, setBirthDate ] = useState(collaborator?.birthDate ? dayjs(collaborator.birthDate) : null);
  const [ startDate, setStartDate ] = useState(collaborator?.startDate ? dayjs(collaborator.startDate) : null);
  const [ seniority, setSeniority ] = useState(collaborator?.seniority);
  const [ skillLevels, setSkillLevels ] = useState(collaborator?.skills || []);
  const [ availableSkills, setAvailableSkills] = useState([]);
  const [ availableSeniorities, setAvailableSeniorities] = useState([]);
  const [ availablePositions, setAvailablePositions] = useState([]);

  dayjs.extend(utc);

  useEffect(() => {
    const load = async () => {
      if(!currentOrganization) {
        return;
      }
      const token = await getAccessTokenSilently();
      const loadedSkills = await listSkills(token, currentOrganization.id);
      const loadedPositions = await listPositions(token, currentOrganization.id);
      const loadedSeniorities = await listSeniorities(token, currentOrganization.id);
      setAvailableSkills(loadedSkills.filter(s => !skillLevels.find(sl => sl.skill.id === s.id)));
      setAvailablePositions(loadedPositions);
      setAvailableSeniorities(loadedSeniorities);
    }
    load();
  }, [currentOrganization, getAccessTokenSilently, skillLevels]);

  const save = async () => {
    const c = {
      id: collaborator?.id,
      email: email,
      firstname: firstname,
      lastname: lastname,
      position: position,
      birthDate: birthDate?.utc().toDate(),
      startDate: startDate?.utc().toJSON(),
      seniority: seniority,
      skills: skillLevels,
    }
    onSave(c);
  };

  return (
    <>
      <Typography variant="h2" sx={{ marginBottom: 2 }} columns>
        {collaborator ? 'Edit collaborator' : 'New collaborator'}
      </Typography>
      <Stack direction={"column"} spacing={5} sx={{marginBottom: 2}}>
        <Box component="fieldset">
          <legend>Personal Information</legend>
          <Stack sx={{ m: 2 }} direction={"row"} spacing={3}>
            <TextField
              required
              fullWidth
              label="First Name"
              value={firstname}
              onChange={(e) => setFirstname(e.target.value)}
            />
            <TextField
              required
              fullWidth
              label="Last Name"
              value={lastname}
              onChange={(e) => setLastname(e.target.value)}
            />
            <TextField
              fullWidth
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
              value={position}
              defaultValue={availablePositions[0]}
              label="Position"
              onChange={(e) => setPosition(e.target.value)}
            >
              {availablePositions?.map((position) => (
                <MenuItem key={position.name} value={position.name}>
                  {position.name}
                </MenuItem>
              ))}
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
        <Box component="fieldset" sx={{ flexWrap: 'wrap'}}>
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
                    setSkillLevels([...skillLevels, { skill: skill, proficiency: 0 }]);
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
                    value={skillLevel.proficiency}
                    onChange={(_, newValue) => {
                      skillLevel.proficiency = newValue;
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
        <Button variant="contained" color="success" onClick={save}>
          Save
        </Button>
        <Button variant="outlined" color="error" onClick={onCancel}>
          Cancel
        </Button>
      </Stack>
    </>
  )
}

export default CollaboratorForm;