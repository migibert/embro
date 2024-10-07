import { useAuth0 } from '@auth0/auth0-react';
import { Add } from '@mui/icons-material';
import { Box, Card, CardActionArea, Stack, Typography } from '@mui/material';
import { React, useContext, useState } from 'react';
import { OrganizationContext } from '../context/OrganizationContext';
import { UserContext } from '../context/UserContext';
import { createOrganization, deleteOrganization } from '../utils/api';
import OrganizationCard from './OrganizationCard';

const Home = () => {
  const { getAccessTokenSilently } = useAuth0();
  const { userInfos, reloadUserInfos, isAllowedToEdit } = useContext(UserContext);
  const { setCurrentOrganization } = useContext(OrganizationContext);

  const [adding, setAdding] = useState(false);

  const defaultOrganization = {
    id: null,
    name: null,
  };

  const saveOrganization = async (organization) => {
    const token = await getAccessTokenSilently();
    let saved = organization;
    if(organization.id) {
      //TODO saved = await updateOrganization(token, organization);
    } else {
      saved = await createOrganization(token, organization);
      setCurrentOrganization(saved);
      setAdding(false);
      reloadUserInfos();
    }
  };

  const removeOrganization = async (organization) => {
    if(organization.id) {
      const token = await getAccessTokenSilently();
      await deleteOrganization(token, organization.id);
    }
    reloadUserInfos();
  }

  const addOrganization = async () => {
    setAdding(true);
  }

  const cancelCreation = () => {
    setAdding(false);
  }

  return (
    <Stack spacing={4} alignItems={'baseline'}>
      <Typography variant="h1">Organizations</Typography>
      <Box display={'flex'} flexWrap={'wrap'} alignItems={'stretch'} gap={2}>
        {adding ? 
          <OrganizationCard
            key="new-organization"
            team={defaultOrganization}
            onDelete={removeOrganization}
            onSave={saveOrganization}
            onSelect={() => {}}
            onCancel={cancelCreation}
            disabled={false}
          />
          :
          <Card key="add-organization" sx={{ width: 250, height: 200, textAlign: 'center' }}>
            <CardActionArea onClick={addOrganization} sx={{ width: '100%', height: '100%'}}>
              <Add sx={{ fontSize: 20 }}/>
            </CardActionArea>
          </Card>
        }
        {userInfos.map((userInfo) => (
          <OrganizationCard
            key={userInfo.organization.id}
            organization={userInfo.organization}
            onDelete={removeOrganization}
            onSave={saveOrganization}
            onSelect={() => setCurrentOrganization(userInfo.organization)}
            disabled={!isAllowedToEdit(userInfo.organization.id)}
          />
        ))}
      </Box>
    </Stack>
  );
};

export default Home;

