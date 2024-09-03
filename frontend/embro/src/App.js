import { useAuth0 } from "@auth0/auth0-react";
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import React, { useEffect, useState } from 'react';
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import Layout from "./components/Layout";
import { OrganizationContext } from "./context/OrganizationContext";
import { listOrganizations } from "./utils/api";
import PrivateRoute from './utils/PrivateRoute';
import routes from "./utils/Routes";

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
        if(userOrganizations && userOrganizations.length > 0) {
          setCurrentOrganization(userOrganizations[0]);
        }
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
    <LocalizationProvider dateAdapter={AdapterDayjs}>
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
    </LocalizationProvider>
  );
}
export default App;

