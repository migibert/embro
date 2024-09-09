
import Collaborators from "../pages/Collaborators";
import Home from "../pages/Home";
import OrganizationSettings from "../pages/OrganizationSettings";
import Profile from "../pages/Profile";
import TeamList from "../pages/Teams";

const routes = [
    
    { text: 'Home', path: '/home', component: <Home />},
    { text: 'Settings', path: '/settings', component: <OrganizationSettings />},
    { text: 'Teams', path: '/teams', component: <TeamList />},
    { text: 'Collaborators', path: '/collaborators', component: <Collaborators />},
    { text: 'Profile', path: '/profile', component: <Profile />},
];

export default routes;