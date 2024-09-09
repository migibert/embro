import { Box, CssBaseline, Drawer, ListItem, ListItemText, Toolbar } from '@mui/material';
import React from 'react';
import { NavLink, Outlet } from 'react-router-dom';
import routes from '../utils/Routes';
import Footer from './Footer';
import Header from './Header';

const drawerWidth = 240;

const Layout = () => {
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
            {routes.map((item) => 
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