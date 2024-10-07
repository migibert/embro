import { Cancel, Delete, PersonAdd, Save } from "@mui/icons-material";
import { Card, CardActions, CardHeader, IconButton, TextField } from "@mui/material";
import { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { OrganizationContext } from "../context/OrganizationContext";
import { getOrganizationColor } from "../utils/Colors";

function OrganizationCard({organization, onDelete, onSave, onCancel, onSelect, disabled}) {
  const [editMode, setEditMode] = useState(false);
  const [name, setName] = useState(organization?.name);
  const { currentOrganization, setCurrentOrganization } = useContext(OrganizationContext);

  const isSelected = () => {
    return organization?.id === currentOrganization?.id;
  }

  const save = () => {
    setEditMode(false);
    const organizationToSave = {id: organization?.id, name: name};
    onSave(organizationToSave);
  };

  const cancel = () => {
    setEditMode(false);
    if(onCancel) {
      onCancel();
    }
  }

  useEffect(() => {
    const load = async () => {
      if(!organization?.id) {
        setEditMode(true);
        return;
      }
      setName(organization?.name);
    }
    load();
  }, [organization])

  return (
    <Card
      onClick={() => onSelect(organization)}
      sx={{
        border: isSelected() ? '2px solid black' : 'none',
        width: 250,
        height: 200,
        justifyContent: 'space-between',
        textAlign: 'center',
        backgroundColor: getOrganizationColor(organization?.id, 80),
        display: 'flex',
        flexDirection: 'column',
      }}
    >
      <CardHeader
        title={editMode === false ? organization?.name : <TextField size="large" value={name} onChange={(e) => setName(e.target.value)} />}
        titleTypographyProps={{ textOverflow: 'ellipsis', overflow: 'hidden', whiteSpace: 'wrap', fontSize: '1.3em' }}
      />
      <CardActions sx={{ justifyContent: 'space-between'}}>
        {editMode && <>
          <IconButton onClick={() => save()}>
            <Save />
          </IconButton>
          <IconButton onClick={() => cancel()}>
            <Cancel />
          </IconButton>
        </>
        }
        {!editMode && !disabled && <>
          <Link
            onClick={() => setCurrentOrganization(organization)} 
            to={'/users'}
          >
            <IconButton>
              <PersonAdd />
            </IconButton>
          </Link>
          <IconButton onClick={() => onDelete(organization)}>
            <Delete/>
          </IconButton>
        </>
      }
      </CardActions>
    </Card>
  );
}

export default OrganizationCard;