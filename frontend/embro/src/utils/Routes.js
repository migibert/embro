
import Collaborators from "../pages/Collaborators";
import Home from "../pages/Home";
import Invitations from "../pages/Invitations";
import OrganizationSettings from "../pages/OrganizationSettings";
import Profile from "../pages/Profile";
import TeamDetails from "../pages/TeamDetails";
import TeamList from "../pages/Teams";

const routes = [
    { text: 'Home', path: '/home', component: <Home />, menu: true, requireOrganization: false},
    { text: 'Settings', path: '/settings', component: <OrganizationSettings />, menu: true, requireOrganization: true},
    { text: 'Teams', path: '/teams', component: <TeamList />, menu: true, requireOrganization: true},
    { text: 'Team', path: '/teams/:teamId', component: <TeamDetails />, menu: false, requireOrganization: true},
    { text: 'Collaborators', path: '/collaborators', component: <Collaborators />, menu: true, requireOrganization: true},
    { text: 'Profile', path: '/profile', component: <Profile />, menu: true, requireOrganization: true},
    { text: 'Invitation', path:'/invitations', component: <Invitations />, menu: false, requireOrganization: true},
    { text: 'Invitation', path:'/invitations/:invitationId', component: <Invitations />, menu: false, requireOrganization: true},
];

export default routes;