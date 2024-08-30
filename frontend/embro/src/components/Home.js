import React from 'react';
import { useAuth0 } from "@auth0/auth0-react";

const Home = () => {
  const { isAuthenticated, loginWithRedirect } = useAuth0();
  if(!isAuthenticated) {
      loginWithRedirect();
  }

  return (
    <div>
      <h1>Home</h1>
    </div>
  );
};

export default Home;

