import { useAuth0 } from "@auth0/auth0-react";
import { Box, Typography } from "@mui/material";
import { useContext, useEffect } from "react";
import { Navigate, useParams } from "react-router-dom";
import { UserContext } from "../context/UserContext";
import { acceptInvitation } from "../utils/api";

function Invitations() {
  let { invitationId } = useParams();
  const { getAccessTokenSilently } = useAuth0()
  const { userInfos, reloadUserInfos } = useContext(UserContext);

  useEffect(() => {
    const load = async () => {
      const token = await getAccessTokenSilently();
      if(invitationId) {
        await acceptInvitation(token, invitationId);
        reloadUserInfos();
      }
    }
    load();
  }, [invitationId, getAccessTokenSilently, userInfos, reloadUserInfos]);

  return <Box textAlign={'center'}>
    <Typography variant="h1">Invitations</Typography>
    <Typography variant="h2">Invitation accepted</Typography>
    <Typography variant="body1">Welcome to the fold!</Typography>
    <Navigate to="/home"/>
  </Box>
}

export default Invitations;