import { useAuth0 } from "@auth0/auth0-react";
import { Box, Dialog, List, ListItem, ListItemText } from "@mui/material";
import { useContext, useEffect, useState } from "react";
import { OrganizationContext } from "../context/OrganizationContext";
import { listCollaborators } from "../utils/api";

function TeamCollaboratorListDialog({open, team, members, onSelect, onClose}) {
  const [collaborators, setCollaborators] = useState([]);
  const { getAccessTokenSilently } = useAuth0();
  const { currentOrganization} = useContext(OrganizationContext);
  const [visible, setVisible] = useState(open);

  const handleSelection = (collaborator) => {
    onSelect(collaborator);
    onClose();
  };

  useEffect(() => {
    setVisible(open);
  }, [open]);

  useEffect(() => {
    const load = async () => {
      if(!team) {
        return;
      }
      const token = await getAccessTokenSilently();
      const loaded = await listCollaborators(token, currentOrganization?.id);
      setCollaborators(loaded.filter((c) => !members.find(m => m.collaboratorId === c.id)));
    }
    load();
  }, [team, members, getAccessTokenSilently, currentOrganization]);

  return (
    <Dialog
      open={visible} 
      onClose={onClose}
    >
      <Box>
        <List dense={true}>
        {collaborators.map((c) => (
          <ListItem key={c.id} button onClick={() => handleSelection(c)}>
            <ListItemText primary={`${c.firstname} ${c.lastname}`} secondary={c.email} />
          </ListItem>
          ))
        }
        </List>
      </Box>
    </Dialog>
  );
}

export default TeamCollaboratorListDialog;