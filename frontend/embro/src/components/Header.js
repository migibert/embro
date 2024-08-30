import { useAuth0 } from '@auth0/auth0-react';
import MenuIcon from '@mui/icons-material/Menu';
import { AppBar, Avatar, Tooltip } from '@mui/material';
import IconButton from '@mui/material/IconButton';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import * as React from 'react';
import { useContext } from 'react';
import { OrganizationContext } from '../context/OrganizationContext';

function Header() {
  const { user, isAuthenticated } = useAuth0();
  const { organizations, currentOrganization, setCurrentOrganization } = useContext(OrganizationContext);
  
  return (
    <AppBar position="fixed" sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}>
      <Toolbar>
        <IconButton size="large" edge="start" color="inherit" aria-label="menu" sx={{ mr: 2 }}>
        <MenuIcon />
        </IconButton>
        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
        EM Bro
        </Typography>
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
        {isAuthenticated && <Avatar src={user.picture} />}
      </Toolbar>
    </AppBar>
  );
}

export default Header;
