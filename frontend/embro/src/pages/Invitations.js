import { useAuth0 } from "@auth0/auth0-react";
import { Box, Button, MenuItem, Select, TextField, Typography } from "@mui/material";
import { useContext, useEffect, useState } from "react";
import { useParams, useSearchParams } from "react-router-dom";
import { OrganizationContext } from "../context/OrganizationContext";
import { acceptInvitation, createInvitation, listOrganizations } from "../utils/api";

function Invitations() {
  let { invitationId } = useParams();
  const { getAccessTokenSilently } = useAuth0()
  const [email, setEmail] = useState();
  const [role, setRole] = useState();
  const [selectedOrganization, setSelectedOrganization] = useState();
  const [searchParams] = useSearchParams();
  const { organizations, setOrganizations } = useContext(OrganizationContext);
  const selectedOrganizationId = searchParams.get('organizationId');  

  const invite = async () => {
    const accessToken = await getAccessTokenSilently();
    await createInvitation(accessToken, email, selectedOrganization.id, role);
    setRole('VIEWER');
    setEmail(null);
  }

  useEffect(() => {
    const load = async () => {
      const token = await getAccessTokenSilently();
      if(invitationId) {
        await acceptInvitation(token, invitationId);
        const reloaded = await listOrganizations(token);
        setOrganizations(reloaded);
      }

      setRole('VIEWER');
      if(selectedOrganizationId) {
        setSelectedOrganization(organizations.find(o => o.id === selectedOrganizationId));
      } else {
        setSelectedOrganization(organizations[0]);
      }
    }
    load();
  }, [invitationId, selectedOrganizationId, getAccessTokenSilently, organizations, setOrganizations]);

  return <Box>
    <Typography variant="h1">Invitations</Typography>
    {invitationId && <Typography variant="h2">Invitation accepted</Typography>}
    {!invitationId && <Box sx={{m: 2}}>
      <Typography variant="h2">Invite user</Typography>
      <Box display="flex" gap={2} sx={{ mt: 2}}>
        <TextField fullWidth type="email" placeholder="Email to invite" onChange={(e) => setEmail(e.target.value)} />
        <Select
          fullWidth
          defaultValue={'VIEWER'}
          value={role}
          onChange={(e) => setRole(e.target.value)}
        >
          {['OWNER', 'EDITOR', 'VIEWER'].map((role) => <MenuItem key={role} value={role}>
            {role}
          </MenuItem>)}
        </Select>
        {selectedOrganization &&
          <Select
            fullWidth
            value={selectedOrganization?.id}
            onChange={(e) => setSelectedOrganization(organizations.find(o => o.id === e.target.value))}
          >
            {organizations.map((organization) => <MenuItem key={organization.id} value={organization.id}>
              {organization.name}
            </MenuItem>)}
          </Select>
        }
      </Box>
      <Box sx={{mt: 2}}>
        <Button variant='contained' onClick={invite}>Invite</Button>
      </Box>
    </Box>}
  </Box>
}

export default Invitations;