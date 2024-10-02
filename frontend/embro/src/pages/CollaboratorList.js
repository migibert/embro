import { Delete } from "@mui/icons-material";
import { IconButton, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";

function CollaboratorList({collaborators, onDelete, onSelect}) {
  const columns = [
    { label: 'First name' },
    { label: 'Last name' },
    { label: 'Email' },
    { label: 'Position' },
    { label: 'Seniority' },
    { label: 'Birth' },
    { label: 'Joined at' },
    { label: 'Actions'}
  ];

  return (
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
              <TableRow key={collaborator.id} onClick={() => onSelect(collaborator)}>
                <TableCell colSpan="2">{collaborator.firstname}</TableCell>
                <TableCell colSpan="2">{collaborator.lastname}</TableCell>
                <TableCell colSpan="2">{collaborator.email}</TableCell>
                <TableCell colSpan="2">{collaborator.position}</TableCell>
                <TableCell colSpan="2">{collaborator.seniority}</TableCell>
                <TableCell colSpan="2">{new Date(collaborator.birthDate).toLocaleDateString()}</TableCell>
                <TableCell colSpan="2">{new Date(collaborator.startDate).toLocaleDateString()}</TableCell>
                <TableCell colSpan="2">
                  <IconButton onClick={() => onDelete(collaborator.id)}>
                    <Delete />
                  </IconButton>
                </TableCell>
              </TableRow>
            )
          )}
        </TableBody>
      </Table>
    </TableContainer>
  )
}

export default CollaboratorList;