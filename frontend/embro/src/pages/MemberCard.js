import { Delete, Star } from "@mui/icons-material";
import { Card, CardActions, CardContent, CardHeader, IconButton, Tooltip, Typography } from "@mui/material";
import { useEffect, useState } from "react";

function MemberCard({member, onDelete, backgroundColor}) {
  const [collaborator, setCollaborator] = useState(member);


  const setKeyPlayer = async () => {
    setCollaborator({ ...collaborator, keyPlayer: !collaborator.keyPlayer });
  }

  useEffect(() => {
    setCollaborator(member);
  }, [member]);

  if(!collaborator) {
    return <></>;
  }
  return (
    <Card sx={{ 
      height: 200, 
      width: 250, 
      padding: 1, 
      justifyContent: 'space-between', 
      display: 'flex', 
      flexDirection: 'column', 
      textAlign: 'justify-center', 
      backgroundColor: backgroundColor}}
    >
      <CardHeader
        sx={{ height: '25%' }}
        title={`${collaborator.firstname} ${collaborator.lastname}`}
        titleTypographyProps={{ textOverflow: 'ellipsis', overflow: 'hidden', whiteSpace: 'wrap' }}
      >
      </CardHeader>
      <CardContent>        
        <Tooltip title={`${collaborator.seniority} ${collaborator.role}`}>
          <Typography variant="body2" color="textSecondary">{collaborator.seniority}</Typography>
          <Typography variant="body2" color="textSecondary">{collaborator.role}</Typography>
        </Tooltip>
      </CardContent>
      <CardActions 
        sx={{
          justifyContent: 'space-between'
        }}>
        <IconButton onClick={() => setKeyPlayer(!member.keyPlayer)}>
          <Star />
        </IconButton>
        <IconButton onClick={() => onDelete(collaborator)}>
          <Delete />
        </IconButton>
      </CardActions>
    </Card>
  );
}

export default MemberCard;