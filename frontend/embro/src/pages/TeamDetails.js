import { useAuth0 } from '@auth0/auth0-react';
import { AdsClick, ChatBubble, Mail, PersonAdd, Phone } from '@mui/icons-material';
import { Box, Button, IconButton, Stack, Typography } from '@mui/material';
import { React, useContext, useEffect, useState } from 'react';
import { Link, useParams } from "react-router-dom";
import EditableIconTextField from '../components/EditableIconTextField';
import { OrganizationContext } from '../context/OrganizationContext';
import { addTeamMember, getTeam, listTeamMembers, removeTeamMember, updateTeam } from '../utils/api';
import MemberCard from './MemberCard';
import TeamCollaboratorListDialog from './TeamCollaboratorListDialog';


const TeamDetails = () => {
  let { teamId } = useParams();
  const { currentOrganization } = useContext(OrganizationContext);
  const { getAccessTokenSilently } = useAuth0();
  const [team, setTeam] = useState();
  const [members, setMembers] = useState([]);
  const [openDialog, setOpenDialog] = useState(false);
  const [colorsByPosition, setColorsByPosition] = useState({});
  const [saturationsBySeniority, setSaturationsBySeniority] = useState({});
  const [lightnessesBySeniority, setLightnessesBySeniority] = useState({});

  const save = async (team) => {
    const token = await getAccessTokenSilently();
    await updateTeam(token, currentOrganization.id, team);
    const loadedTeam = await getTeam(token, currentOrganization.id, team.id);
    setTeam(loadedTeam);
  };

  const addMember = async (collaborator) => {
    const token = await getAccessTokenSilently();
    const member = await addTeamMember(token, currentOrganization.id, team.id, collaborator.id, false);
    setMembers([...members, member]);
  };

  const removeMember = async (member) => {
    const token = await getAccessTokenSilently();
    await removeTeamMember(token, currentOrganization.id, team.id, member.collaboratorId);
    setMembers(members.filter(m => m.collaboratorId !== member.collaboratorId));
  };

  const getMemberColor = (member) => {
    if(!colorsByPosition[member.position]) {
      colorsByPosition[member.position] = 360 * Math.random();
      setColorsByPosition(colorsByPosition);
    }
    if(!saturationsBySeniority[member.seniority]) {
      saturationsBySeniority[member.seniority] = 25 + 25 * Math.random(); // 25-50%
      setSaturationsBySeniority(saturationsBySeniority);
    }
    if(!lightnessesBySeniority[member.seniority]) {
      lightnessesBySeniority[member.seniority] = 75 + 25 * Math.random(); // 75-100%
      setLightnessesBySeniority(lightnessesBySeniority);
    }
    return `hsl(
      ${colorsByPosition[member.position]},
      ${saturationsBySeniority[member.seniority]}%,
      ${lightnessesBySeniority[member.seniority]}%
    )`;
  }

  useEffect(() => {
    const load = async () => {
      if(!currentOrganization) {
        return;
      }
      const token = await getAccessTokenSilently();
      const loadedTeam = await getTeam(token, currentOrganization.id, teamId);
      const loadedMembers = await listTeamMembers(token, currentOrganization.id, teamId);
      setTeam(loadedTeam);
      setMembers(loadedMembers);
    };
    if(!teamId) {
      return;
    }
    load();
  }, [teamId, getAccessTokenSilently, currentOrganization]);

  return (
    <Stack spacing={5}>
      <Typography variant='h1'>{team?.name}</Typography>
      <Box>
        <Typography variant='h2'>Mission</Typography>
        <EditableIconTextField fullWidth icon={<AdsClick />} value={team?.mission} onSave={(v) => save({...team, mission: v})} />
      </Box>
      <Box display={'flex'} gap={2}>
        <Box flexGrow={1} mr={4}>
          <Typography variant='h2'>Contacts</Typography>
          <Box display={'flex'} flexDirection={'column'} gap={1}>
            <EditableIconTextField icon={<Mail />} value={team?.email} onSave={(v) => save({...team, email: v})} />
            <EditableIconTextField icon={<Phone />} value={team?.phone} onSave={(v) => save({...team, phone: v})} />
            <EditableIconTextField icon={<ChatBubble />} value={team?.instantMessage} onSave={(v) => save({...team, instantMessage: v})} />
          </Box>
        </Box>
        <Box flexGrow={1} ml={4}>
          <Typography variant='h2'>Metrics</Typography>
          <Box display='flex' flexDirection={'column'} gap={2}>
            TODO
          </Box>
        </Box>
      </Box>
      <Box>
        <Stack direction={'row'} spacing={5}>
          <Typography variant='h2'>Members</Typography>
          <IconButton size='large' onClick={() => setOpenDialog(true)}>
            <PersonAdd fontSize='2rem'/>
          </IconButton>
          <TeamCollaboratorListDialog
            open={openDialog}
            team={team}
            members={members}
            onClose={() => setOpenDialog(false)}
            onSelect={(collaborator) => addMember(collaborator)}
          />
        </Stack>
        <Stack spacing={2} direction={'row'} flexWrap={'wrap'} useFlexGap>
          {members?.map(m => <MemberCard
            key={m.id}
            backgroundColor={getMemberColor(m)}
            member={m}
            onDelete={() => removeMember(m)} />)
          }
        </Stack>
      </Box>
      <Typography variant='h2'>Statistics</Typography>
      <Box display={'flex'} justifyItems={'center'} gap={2}>
        <Link to="/teams">
          <Button size="large" variant='contained' color='primary'>Back</Button>
        </Link>
      </Box>
    </Stack>
  );
};

export default TeamDetails;