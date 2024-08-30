
import Home from "./components/Home";
import OrganizationList from "./components/OrganizationList";
import Profile from "./components/Profile";
import TeamList from "./components/TeamList";

const routes = [
    { text: 'Home', path: '/home', component: <Home />},
    { text: 'Profile', path: '/profile', component: <Profile />},
    { text: 'Organizations', path: '/organizations', component: <OrganizationList />},
    { text: 'Teams', path: '/teams', component: <TeamList />},
]

export default routes;