import { useAuth0 } from "@auth0/auth0-react";
import { Box, Button, List, ListItem, MenuItem, Select, TextField, Typography } from "@mui/material";
import { useContext, useEffect, useState } from "react";
import { OrganizationContext } from "../context/OrganizationContext";
import { UserContext } from "../context/UserContext";
import { createInvitation, deleteInvitation, deleteUser, listInvitations, listUsers, updateUser } from "../utils/api";


function Users() {
  const roles = ['VIEWER', 'EDITOR', 'OWNER'];
  const [ users, setUsers ] = useState([]);
  const [ invitations, setInvitations ] = useState([]);
  const { getAccessTokenSilently, user } = useAuth0();
  const { currentOrganization } = useContext(OrganizationContext);
  const { isAllowedToAdmin, reloadUserInfos } = useContext(UserContext);
  const [ adding, setAdding ] = useState(false);
  const [ email, setEmail ] = useState();
  const [ role, setRole ] = useState(roles[0]);

  const isSelf = (userEmail) => {
    return user?.email === userEmail;
  }

  const invite = async () => {
    const accessToken = await getAccessTokenSilently();
    const created = await createInvitation(accessToken, email, currentOrganization?.id, role);
    setRole(roles[0]);
    setEmail(null);
    setAdding(false);
    setInvitations([...invitations, created]);
  }

  const removeInvitation = async (invitationId) => {
    const token = await getAccessTokenSilently();
    await deleteInvitation(token, invitationId);
    setInvitations(invitations.filter(i => i.id !== invitationId));
  }

  const update = async (email, role) => {
    const token = await getAccessTokenSilently();
    const user = {email, role};
    await updateUser(token, currentOrganization.id, user);
  }

  const remove = async (email) => {
    const token = await getAccessTokenSilently();
    await deleteUser(token, currentOrganization.id, email);
    reloadUserInfos();
  };

  const updateRole = (email, role) => {
    setUsers(users.map(u => {
      if(u.email === email) {
        u.role = role;
      }
      return u;
    }));
  }

  useEffect(() => {
    const load = async () => {
      if(!currentOrganization) {
        return;
      }
      const token = await getAccessTokenSilently();
      const loadedUsers  = await listUsers(token, currentOrganization.id);
      const loadedInvitations = await listInvitations(token, currentOrganization.id);
      setUsers(loadedUsers);
      setInvitations(loadedInvitations);
    };
    load();
  }, [getAccessTokenSilently, currentOrganization]);

  return (
    <Box>
      <Typography variant="h1">Users</Typography>
      <Box p={2} mt={2}>
        {!adding && (
          <Button
            disabled={!isAllowedToAdmin(currentOrganization?.id)}
            size="large" 
            variant="contained"
            onClick={() => setAdding(true)}>
            Invite
          </Button>
        )}
        {adding && (
          <Box display={'flex'} gap={2}>
            <TextField value={email} onChange={(e) => setEmail(e.target.value)} label="Email" fullWidth />
            <Select
              disabled={!isAllowedToAdmin(currentOrganization.id) || isSelf(email)}
              value={role}
              onChange={(e) => setRole(e.target.value)}
              fullWidth
            >
              {roles.map(r => (
                <MenuItem key={r} value={r}>{r}</MenuItem>
              ))}
            </Select>
            <Box display="flex" gap={2}>
              <Button
                disabled={!isAllowedToAdmin(currentOrganization.id) || isSelf(email)}
                size="large" 
                variant="contained"
                color="primary"
                onClick={invite}
              >
                Invite
              </Button>
              <Button
                disabled={!isAllowedToAdmin(currentOrganization.id) || isSelf(email)}
                size="large" 
                variant="contained"
                onClick={() => setAdding(false)}
                color='error'>
                Cancel
              </Button>
            </Box>
          </Box>
        )}
      </Box>
      <List sx={{ mt: 2 }}>
        {users?.map(organizationUser => (
          <ListItem
            key={organizationUser.email}
            sx={{
              flex: 1,
              display: 'flex', 
              alignItems: 'stretch',
              justifyContent: 'space-between',
              textAlign: 'center',
              gap: 2,
            }} 
          >
              <TextField fullWidth disabled value={organizationUser.email} />
              <Select
                disabled={!isAllowedToAdmin(currentOrganization.id)}
                fullWidth
                value={organizationUser.role} 
                onChange={(e) => updateRole(organizationUser.email, e.target.value)}
              >
                {roles.map(r => (
                  <MenuItem key={r} value={r}>{r}</MenuItem>
                ))}
              </Select>
              <Box display='flex' gap={1}>
                <Button
                  disabled={!isAllowedToAdmin(currentOrganization.id)}
                  variant="contained"
                  color="primary"
                  onClick={() => update(organizationUser.email, organizationUser.role)}
                >
                    Save
                </Button>
                <Button
                  disabled={!isAllowedToAdmin(currentOrganization.id) && !isSelf(organizationUser.email)}
                  variant="contained"
                  color="error"
                  onClick={() => remove(organizationUser.email, organizationUser.role)}
                >
                  { isSelf(organizationUser.email) ? 'Leave' : 'Remove'}
                </Button>
              </Box>
          </ListItem>
        ))}
      </List>
      <Box>
        <Typography variant="h2">Invitations</Typography>
        <List sx={{ mt: 2 }}>
          {invitations?.map(invitation => (
            <ListItem
              key={invitation.id}
              sx={{
                flex: 1,
                display: 'flex', 
                alignItems: 'stretch',
                justifyContent: 'space-between',
                textAlign: 'center',
                gap: 2,
              }}
            >
              <TextField fullWidth disabled value={invitation.email} />
              <TextField fullWidth disabled value={invitation.role} />  
              <Button 
                disabled={!isAllowedToAdmin(currentOrganization.id)}
                size="large"
                variant="contained"
                color="error"
                onClick={() => removeInvitation(invitation.id)}
              >
                  Delete
              </Button>
            </ListItem>
          ))}
        </List>
      </Box>
    </Box>
    
  );
}

export default Users;