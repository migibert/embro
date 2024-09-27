import { useAuth0 } from "@auth0/auth0-react";
import { Box, Dialog, DialogTitle, List, ListItem, ListItemText, TextField } from "@mui/material";
import { useContext, useEffect, useState } from "react";
import { OrganizationContext } from "../context/OrganizationContext";
import { listCollaborators } from "../utils/api";

function TeamCollaboratorListDialog({open, team, members, onSelect, onClose}) {
  const [collaborators, setCollaborators] = useState([]);
  const { getAccessTokenSilently } = useAuth0();
  const { currentOrganization} = useContext(OrganizationContext);
  const [visible, setVisible] = useState(open);
  const [filteredCollaborators, setFilteredCollaborators] = useState([]);
  const [search, setSearch] = useState('');

  const handleSelection = (collaborator) => {
    onSelect(collaborator);
    onClose();
  };

  const handleSearchChange = (e) => {
    setSearch(e.target.value);
    setFilteredCollaborators(filterCollaborators(e.target.value));
  };

  const filterCollaborators = (firstChars) => {
    return (
      collaborators.filter((c) => (
        c.firstname.toLowerCase().startsWith(firstChars.toLowerCase()) ||
        c.lastname.toLowerCase().startsWith(firstChars.toLowerCase()))
      ).filter(c => !members.find(m => m.collaboratorId === c.id))
    );
  }

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
      setFilteredCollaborators(loaded.filter(c => !members.find(m => m.collaboratorId === c.id)));
      setCollaborators(loaded);
      console.log(loaded);
    }
    load();
  }, [team, members, getAccessTokenSilently, currentOrganization]);

  return (
    <Dialog open={visible} onClose={onClose}>
      <DialogTitle>Pick a collaborator to add to {team?.name}</DialogTitle>
      <TextField
        label="Search"
        variant="outlined"
        value={search}
        onChange={handleSearchChange}
        sx={{ m: 2 }}
      />
      <Box>
        <List>
        {filteredCollaborators.map((c) => (
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