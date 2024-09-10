import { useAuth0 } from '@auth0/auth0-react';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import { IconButton, Typography } from '@mui/material';
import { React, useContext, useEffect, useState } from 'react';
import { OrganizationContext } from '../context/OrganizationContext';
import { createCollaborator, deleteCollaborator, listCollaborators, updateCollaborator } from '../utils/api';
import CollaboratorForm from './CollaboratorForm';
import CollaboratorList from './CollaboratorList';

const Collaborators = () => {
  const { getAccessTokenSilently } = useAuth0();
  const { currentOrganization} = useContext(OrganizationContext);
  const [collaborators, setCollaborators] = useState([]);
  const [adding, setAdding] = useState(false);
  const [selected, setSelected] = useState(null);

  const removeCollaborator = async (id) => {
    const token = await getAccessTokenSilently();
    await deleteCollaborator(token, currentOrganization.id, id);
    setCollaborators(collaborators.filter((collaborator) => collaborator.id !== id));
  }

  const saveCollaborator = async (collaborator) => {
    const token = await getAccessTokenSilently();
    if(collaborator.id) {
      const updated = await updateCollaborator(token, currentOrganization.id, collaborator);
      const filtered = collaborators.filter((c) => collaborator.id !== c.id)
      setCollaborators([...filtered, updated]);
    } else {
      const created = await createCollaborator(token, currentOrganization.id, collaborator);
      setCollaborators([...collaborators, created]);
    }
  }

  useEffect(() => {
    const load = async () => {
      if(!currentOrganization) {
        return;
      }
      const token = await getAccessTokenSilently();
      const loaded = await listCollaborators(token, currentOrganization.id);
      setCollaborators(loaded);
    };
    load();
  }, [getAccessTokenSilently, currentOrganization]);

  return (
    <div>
      <Typography variant="h1">Collaborators</Typography>
      <CollaboratorList 
        collaborators={collaborators}
        onDelete={(id) => {
          setAdding(false);
          setSelected(null);
          removeCollaborator(id);
        }}
        onSelect={(c) => setSelected(c)}/>
      {!adding && !selected && (
        <IconButton onClick={() => setAdding(true)}>
          <AddCircleIcon/>
        </IconButton>
      )}
      
      {(adding || selected) &&
        <CollaboratorForm 
          collaborator={selected}
          onSubmitted={(submitted) => {
            setAdding(false);
            setSelected(null);
            saveCollaborator(submitted)
          }}
          onCancelled={() => {
            setAdding(false);
            setSelected(null);
          }}
        />
      }
    </div>
  );
};

export default Collaborators;

