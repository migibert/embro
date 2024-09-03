import { useAuth0 } from '@auth0/auth0-react';
import { Delete } from '@mui/icons-material';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import { IconButton, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography } from '@mui/material';
import { React, useContext, useEffect, useState } from 'react';
import CollaboratorForm from '../components/CollaboratorForm';
import { OrganizationContext } from '../context/OrganizationContext';
import { deleteCollaborator, listCollaborators } from '../utils/api';

const Collaborators = () => {
  const { getAccessTokenSilently } = useAuth0();
  const { currentOrganization} = useContext(OrganizationContext);
  const [collaborators, setCollaborators] = useState([]);
  const [adding, setAdding] = useState(false);

  const removeCollaborator = async (id) => {
    const token = await getAccessTokenSilently();
    await deleteCollaborator(token, currentOrganization.id, id);
    setCollaborators(collaborators.filter((collaborator) => collaborator.id !== id));
  }

  const columns = [
    { label: 'First name' },
    { label: 'Last name' },
    { label: 'Email' },
    { label: 'Role' },
    { label: 'Seniority' },
    { label: 'Birth' },
    { label: 'Joined at' },
    { label: 'Actions'}
  ]

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
      <TableContainer component={Paper} sx={{marginTop: 2, marginBottom: 2}}>
        <Table>
          <TableHead>
            <TableRow>
              {columns.map(
                (column) => (
                  <TableCell colSpan="2" key={column.label}>
                    {column.label}
                  </TableCell>
                ))}
            </TableRow>
          </TableHead>
          <TableBody>
            {collaborators?.map(
              (collaborator) => (
                <TableRow key={collaborator.id}>
                  <TableCell colSpan="2">{collaborator.firstname}</TableCell>
                  <TableCell colSpan="2">{collaborator.lastname}</TableCell>
                  <TableCell colSpan="2">{collaborator.email}</TableCell>
                  <TableCell colSpan="2">{collaborator.role}</TableCell>
                  <TableCell colSpan="2">{collaborator.seniority}</TableCell>
                  <TableCell colSpan="2">{new Date(collaborator.birthDate).toLocaleDateString()}</TableCell>
                  <TableCell colSpan="2">{new Date(collaborator.startDate).toLocaleDateString()}</TableCell>
                  <TableCell colSpan="2">
                    <IconButton onClick={() => removeCollaborator(collaborator.id)}>
                      <Delete />
                    </IconButton>
                  </TableCell>
                </TableRow>
              )
            )}
          </TableBody>
        </Table>
      </TableContainer>
      {adding === false && (
        <IconButton onClick={() => setAdding(true)}>
          <AddCircleIcon/>
        </IconButton>
      )}
      
      {adding && 
        <CollaboratorForm 
          onSubmitted={(created) => {
            setAdding(false);
            setCollaborators([...collaborators, created]);
          }}
          onCancelled={() => setAdding(false)}
        />
      }
    </div>
  );
};

export default Collaborators;

