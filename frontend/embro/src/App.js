import { useAuth0 } from "@auth0/auth0-react";
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import React, { useCallback, useEffect, useState } from 'react';
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import Layout from "./components/Layout";
import { OrganizationContext } from "./context/OrganizationContext";
import { UserContext } from "./context/UserContext";
import { me } from "./utils/api";
import PrivateRoute from './utils/PrivateRoute';
import routes from "./utils/Routes";

function App() {
  const { isLoading, isAuthenticated, loginWithRedirect, getAccessTokenSilently } = useAuth0();
  const [currentOrganization, setCurrentOrganization] = useState();
  const [userInfos, setUserInfos] = useState([]);

  const loadUserInfos = useCallback(async () => {
    const token = await getAccessTokenSilently();
    const userInfos = await me(token);
    setUserInfos(userInfos);
    if(!currentOrganization || !userInfos.find((userInfo) => userInfo.organization.id === currentOrganization.id)) { 
      if(userInfos.length > 0) {
        setCurrentOrganization(userInfos[0].organization);
      }
    }
  }, [getAccessTokenSilently, currentOrganization]);

  const hasOneOfRolesInOrganization = (organizationId, roles) => {
    if(!organizationId) {
      return false;
    }
    if(!roles) {
      return false;
    }
    const userInfo = userInfos.find((userInfo) => userInfo.organization.id === organizationId);
    if(!userInfo) {
      return false;
    }
    return roles.includes(userInfo.role);
  }


  const isAllowedToAdmin = (organizationId) => {
    return hasOneOfRolesInOrganization(organizationId, ['OWNER']);
  }

  const isAllowedToEdit = (organizationId) => {
    return hasOneOfRolesInOrganization(organizationId, ['OWNER', 'EDITOR']);
  }

  useEffect(() => {
    loadUserInfos();
  }, [loadUserInfos]);
  
  if(isLoading) {
      return <div>Loading...</div>;
  }

  if(!isAuthenticated) {
    loginWithRedirect();
  }
  
  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <UserContext.Provider value={{
        userInfos: userInfos,
        reloadUserInfos: loadUserInfos,
        isAllowedToEdit: isAllowedToEdit,
        isAllowedToAdmin: isAllowedToAdmin
      }}>
        <OrganizationContext.Provider value={{
          currentOrganization, setCurrentOrganization
        }}>
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
      </UserContext.Provider>
    </LocalizationProvider>
  );
}
export default App;

