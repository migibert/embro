
import Collaborators from "../pages/Collaborators";
import Home from "../pages/Home";
import OrganizationSettings from "../pages/OrganizationSettings";
import Profile from "../pages/Profile";
import TeamDetails from "../pages/TeamDetails";
import TeamList from "../pages/Teams";

const routes = [
    
    { text: 'Home', path: '/home', component: <Home />, menu: true},
    { text: 'Settings', path: '/settings', component: <OrganizationSettings />, menu: true},
    { text: 'Teams', path: '/teams', component: <TeamList />, menu: true},
    { text: 'Team', path: '/teams/:teamId', component: <TeamDetails />, menu: false},
    { text: 'Collaborators', path: '/collaborators', component: <Collaborators />, menu: true},
    { text: 'Profile', path: '/profile', component: <Profile />, menu: true},
];

export default routes;