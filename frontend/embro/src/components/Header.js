import { useAuth0 } from '@auth0/auth0-react';
import { AppBar, Avatar, Stack, Tooltip } from '@mui/material';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import * as React from 'react';
import { useContext } from 'react';
import { Link } from 'react-router-dom';
import { OrganizationContext } from '../context/OrganizationContext';
import { UserContext } from '../context/UserContext';
import { getOrganizationColor } from '../utils/Colors';

function Header() {
  const { user, isAuthenticated } = useAuth0();
  const { currentOrganization, setCurrentOrganization } = useContext(OrganizationContext);
  const { userInfos } = useContext(UserContext);  

  return (
    <AppBar position="fixed" sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}>
      <Toolbar>
        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
        EM Bro
        </Typography>
        <Stack spacing={2} direction='row'>
          {userInfos?.map((userInfo) =>
          <Tooltip title={userInfo.organization.name} key={userInfo.organization.id}>
            <Avatar
              onClick={() => setCurrentOrganization(userInfo.organization)}
              sx={{
                backgroundColor: getOrganizationColor(userInfo.organization.id, 60),
                border: (currentOrganization.id === userInfo.organization.id ? '2px solid black' : 'none')
              }}
              style={{cursor: 'pointer'}}
              alt={userInfo.organization.name}
            >
              {userInfo.organization.name.split(' ').map(i => i.charAt(0)).join('')}
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
