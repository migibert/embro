import { useAuth0 } from '@auth0/auth0-react';
import { AppBar, Avatar, Stack, Tooltip } from '@mui/material';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import * as React from 'react';
import { useContext } from 'react';
import { Link } from 'react-router-dom';
import { OrganizationContext } from '../context/OrganizationContext';

function Header() {
  const { user, isAuthenticated } = useAuth0();
  const { organizations, currentOrganization, setCurrentOrganization } = useContext(OrganizationContext);
  
  return (
    <AppBar position="fixed" sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}>
      <Toolbar>
        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
        EM Bro
        </Typography>
        <Stack spacing={2} direction='row'>
          {organizations?.map((organization) =>
          <Tooltip title={organization.name} key={organization.id}>
            <Avatar
              onClick={() => setCurrentOrganization(organization)}
              sx={(currentOrganization === organization ? { border: '2px solid black'} : null)}
              style={{cursor: 'pointer'}}
              alt={organization.name}
            >
              {organization.name.charAt(0)}
            </Avatar>
          </Tooltip>
          )}
          {isAuthenticated && <Link to="/profile"><Avatar src={user.picture} /></Link>} 
        </Stack>
      </Toolbar>
    </AppBar>
  );
}

export default Header;
