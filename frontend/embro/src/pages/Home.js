import { useAuth0 } from '@auth0/auth0-react';
import { Add } from '@mui/icons-material';
import { Box, Card, CardActionArea, Stack, Typography } from '@mui/material';
import { React, useContext, useState } from 'react';
import { OrganizationContext } from '../context/OrganizationContext';
import { createOrganization, deleteOrganization } from '../utils/api';
import OrganizationCard from './OrganizationCard';

const Home = () => {
  const { getAccessTokenSilently } = useAuth0();
  const { setCurrentOrganization, organizations, setOrganizations } = useContext(OrganizationContext);
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
      setOrganizations([...organizations, saved].filter(o => o.id !== null));
      setCurrentOrganization(saved);
      setAdding(false);
    }
  };

  const removeOrganization = async (organization) => {
    setOrganizations(organizations.filter(o => o.id !== organization.id));
    if(organization.id) {
      const token = await getAccessTokenSilently();
      await deleteOrganization(token, organization.id);
    }
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
            onCancel={cancelCreation}
          />
          :
          <Card key="add-organization" sx={{ width: 200, height: 200, textAlign: 'center' }}>
            <CardActionArea onClick={addOrganization} sx={{ width: '100%', height: '100%'}}>
              <Add sx={{ fontSize: 20 }}/>
            </CardActionArea>
          </Card>
        }
        {organizations?.map((organization) => (
          <OrganizationCard key={organization.id} organization={organization} onDelete={removeOrganization} onSave={saveOrganization}/>
        ))}
      </Box>
    </Stack>
  );
};

export default Home;

