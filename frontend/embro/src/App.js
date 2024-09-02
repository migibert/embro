import { useAuth0 } from "@auth0/auth0-react";
import React, { useEffect, useState } from 'react';
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import Layout from "./components/Layout";
import PrivateRoute from './components/PrivateRoute';
import { OrganizationContext } from "./context/OrganizationContext";
import routes from "./Routes";
import { listOrganizations } from "./utils/api";

function App() {
  const { isLoading, isAuthenticated, loginWithRedirect, getAccessTokenSilently } = useAuth0();
  const [currentOrganization, setCurrentOrganization] = useState();
  const [organizations, setOrganizations] = useState([]);

  useEffect(() => {
    const init = async () => {
      if(isAuthenticated) {
        const token = await getAccessTokenSilently();
        const userOrganizations = await listOrganizations(token);
        setOrganizations(userOrganizations);
      }
    }
    init();
  }, [getAccessTokenSilently, isAuthenticated]);
  
  if(isLoading) {
      return <div>Loading...</div>;
  }

  if(!isAuthenticated) {
    loginWithRedirect();
  }
  
  return (
    <OrganizationContext.Provider value={{organizations, setOrganizations, currentOrganization, setCurrentOrganization}}>
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

