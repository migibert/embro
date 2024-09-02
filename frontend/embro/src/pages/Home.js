import { useAuth0 } from "@auth0/auth0-react";
import { Typography } from '@mui/material';
import React from 'react';
import OrganizationList from "../components/OrganizationList";

const Home = () => {
  const { isAuthenticated, loginWithRedirect } = useAuth0();
  if(!isAuthenticated) {
      loginWithRedirect();
  }

  return (
    <div>
      <Typography variant='h1'>Home</Typography>
      <OrganizationList />
    </div>
  );
};

export default Home;

