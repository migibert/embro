import { Box, Button, Stack, TextField } from "@mui/material";
import { React, useState } from 'react';


const TeamDetails = ({team, onSave, onCancel}) => {
  const [name, setName] = useState(team?.name);
  const [description, setDescription] = useState(team?.description);
  const save = () => {
    onSave({
      id: team?.id,
      name: name,
    });
  }

  return (
    <Box component="fieldset">
      <legend>Team Details</legend>
      <Stack sx={{ mt: 4 }} spacing={2}>
        <TextField 
          required 
          id="team-name" 
          label="Name" 
          variant="filled" 
          value={name} 
          onChange={(e) => setName(e.target.value)}/>
        <TextField 
          required 
          id="team-description" 
          label="Description" 
          variant="filled" 
          onChange={(e) => setDescription(e.target.value)}/>
        <Stack direction={"row"} spacing={2} sx={{mt: 2}}>
          <Button variant="contained" color="success" onClick={save}>
            Save
          </Button>
          <Button variant="contained" color="error" onClick={onCancel}>
            Cancel
          </Button>
        </Stack>
      </Stack>
    </Box>
  );
};

export default TeamDetails;