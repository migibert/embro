import { Cancel, Edit, Save } from "@mui/icons-material";
import { Box, IconButton, TextField } from "@mui/material";
import { useEffect, useState } from "react";

function EditableIconTextField({icon, value, onSave}) {
  const [text, setText] = useState(value);
  const [editing, setEditing] = useState(false);

  useEffect(() => {
    setText(value);
  }, [value]);

  const save = () => {
    setEditing(false);
    onSave(text);
  }

  return (
    <Box display='flex' gap={2} alignItems={'center'} flexWrap={'wrap'}>
      {icon}
      <TextField 
        style={{flexGrow: 1}}
        value={text}
        disabled={!editing}
        onChange={(e) => setText(e.target.value)}
      />
      {
        editing ?
        <>
          <IconButton size="large" edge="end" aria-label="save" onClick={save}>
            <Save />
          </IconButton>
          <IconButton size="large" edge="end" aria-label="cancel" onClick={() => setEditing(false)}>
            <Cancel />
          </IconButton>
        </>
        :
        <IconButton edge="end" aria-label="edit" onClick={() => setEditing(true)}>
          <Edit />
        </IconButton>
      }
    </Box>
  );
}

export default EditableIconTextField;