import { Box, CssBaseline, Drawer, ListItem, ListItemText, Toolbar } from '@mui/material';
import React, { useContext } from 'react';
import { NavLink, Outlet } from 'react-router-dom';
import { OrganizationContext } from '../context/OrganizationContext';
import routes from '../utils/Routes';
import Footer from './Footer';
import Header from './Header';

const drawerWidth = 240;

const Layout = () => {
  const { currentOrganization } = useContext(OrganizationContext);

  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', height: '100vh' }}>
      <CssBaseline />
      <Header />
      <Box sx={{ display: 'flex', flexGrow: 1, mt: '64px' }}>
        <Drawer 
          variant="permanent" 
          sx={{
            width: drawerWidth,
            flexShrink: 0,
            [`& .MuiDrawer-paper`]: { width: drawerWidth, boxSizing: 'border-box' },
          }}
        >
          <Toolbar />
          <Box sx={{ overflow: 'auto'}}>
            {routes
              .filter((route) => route.menu === true)
              .filter((route) => currentOrganization || !route.requireOrganization)
              .map((item) => 
                <ListItem button 
                  key={item.text} 
                  component={NavLink} 
                  to={item.path}
                  sx={{
                    '&.active': {
                      backgroundColor: 'lightgray',
                    },
                    color: 'text.primary',
                  }}
                >
                  <ListItemText primary={item.text} />
                </ListItem>
            )}
          </Box>
        </Drawer>
        <Box
          component="main"
          sx={{
            flexGrow: 1,
            bgcolor: 'background.default',
            p: 3,
          }}
        >
          <Outlet/>
        </Box>
      </Box>
      <Footer/>
    </Box>
  );
};

export default Layout;