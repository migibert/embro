import { Cancel } from '@mui/icons-material';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import Delete from "@mui/icons-material/Delete";
import SaveRoundedIcon from '@mui/icons-material/SaveRounded';
import { Box, IconButton, List, ListItem, ListItemText, TextField } from "@mui/material";
import { useState } from "react";

function FieldsetList({title, items, onSave, onDelete, disabled}) {
  const [name, setName] = useState(null);
  const [adding, setAdding] = useState(false);

  return (
    <Box component="fieldset" style={{height:'100%'}}>
      <legend>{title}</legend>
      <List dense="true">
        {items?.map((item) => 
          <ListItem 
            key={item.id}
            secondaryAction={
              <IconButton 
                edge="end"
                aria-label="delete"
                disabled={disabled}
                onClick={() => onDelete(item.id)}
              >
                <Delete />
              </IconButton>
            }
          >
            <ListItemText primary={item.name} />
          </ListItem>
        )}
      </List>
      {!adding && 
      <IconButton 
        disabled={disabled}
        onClick={() => setAdding(true)}
      >
        <AddCircleIcon/>
      </IconButton>
      }
      {adding && 
      <Box>
        <TextField
          required
          id="new-item-name"
          disabled={disabled}
          label="Name"
          variant="filled"
          onChange={(e) => setName(e.target.value)}
        />
        <IconButton
          disabled={disabled}
          onClick={() => {
            onSave(name);
            setAdding(false);
          }}
        >
          <SaveRoundedIcon/>
        </IconButton>
        <IconButton
          disabled={disabled}
          onClick={() => setAdding(false)}
        >
          <Cancel/>
        </IconButton>
      </Box>
      }
    </Box>
  );
};

export default FieldsetList;