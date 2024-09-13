import { useAuth0 } from "@auth0/auth0-react";
import { Delete, Group, Info } from "@mui/icons-material";
import { Card, CardActions, CardContent, CardHeader, IconButton } from "@mui/material";
import { useContext, useEffect, useState } from "react";
import { OrganizationContext } from "../context/OrganizationContext";
import { listTeamMembers } from "../utils/api";

function TeamCard({team, onDelete}) {

  const { currentOrganization } = useContext(OrganizationContext);
  const [members, setMembers] = useState([]);
  const { getAccessTokenSilently } = useAuth0();
  
  useEffect(() => {
    const load = async () => {
      if(!team) {
        return;
      }
      const token = await getAccessTokenSilently();
      const loadedMembers = await listTeamMembers(token, currentOrganization.id, team.id);
      setMembers(loadedMembers);
    }
    load();
  }, [team, currentOrganization, getAccessTokenSilently])

  if(team === null) {
    return <div/>;
  }

  return (
    <Card>
      <CardHeader title={team?.name} />
      <CardContent>
        {members.map(member => (
          <div key={member.id}>{member.name}</div>
        ))}
      </CardContent>
      <CardActions>
        <IconButton>
          <Info/>
        </IconButton>
        <IconButton>
          <Group/>
        </IconButton>
        <IconButton onClick={() => onDelete(team)}>
          <Delete/>
        </IconButton>
      </CardActions>
    </Card>
  );
}

export default TeamCard;