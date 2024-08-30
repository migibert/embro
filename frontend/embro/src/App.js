import { useAuth0 } from "@auth0/auth0-react";
import React, { useState } from 'react';
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import Layout from "./components/Layout";
import PrivateRoute from './components/PrivateRoute';
import { OrganizationContext } from "./context/OrganizationContext";
import { useOrganizations } from "./hooks/useOrganizations";
import routes from "./Routes";

function App() {
  const { isLoading, isAuthenticated, loginWithRedirect } = useAuth0();
  const [currentOrganization, setCurrentOrganization] = useState();
  //const [organizations, setOrganizations] = useState([]);
  const organizations = useOrganizations();
  if(isLoading) {
      return <div>Loading...</div>;
  }

  if(!isAuthenticated) {
    loginWithRedirect();
  }
  
  return (
    <OrganizationContext.Provider value={{organizations, currentOrganization, setCurrentOrganization}}>
      <Router>
        <Routes>
          <Route path="/" element={<Layout/>}>
            {routes.map((route) => (
              <Route 
                key={route.path}
                path={route.path}
                element={<PrivateRoute>{route.component}</PrivateRoute>}
              />
            ))}
          </Route>
        </Routes>
      </Router>
    </OrganizationContext.Provider>
  );
}
export default App;

