import { useAuth0 } from '@auth0/auth0-react';
import { useEffect, useState } from 'react';
import { get } from '../utils/api';

function useTeams(organizationId) {
    const [teams, setTeams] = useState([]);
    const { getAccessTokenSilently } = useAuth0();
    
    useEffect(() => {
      const call = async () => {
        try {
          const token = await getAccessTokenSilently();
          const data = await get(token, `http://localhost:8080/organizations/${organizationId}/teams/`);
          setTeams(data);
        } catch(e) {
          console.error(e);
          setTeams([]);
        }
      };
      if(organizationId) {
        call();
      }
    }, [getAccessTokenSilently, organizationId]);
    return teams;
};

export { useTeams };
