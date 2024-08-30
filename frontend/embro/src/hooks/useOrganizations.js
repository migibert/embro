import { useAuth0 } from '@auth0/auth0-react';
import { useEffect, useState } from 'react';
import { get } from '../utils/api';

function useOrganizations() {
    const [organizations, setOrganizations] = useState([]);
    const { getAccessTokenSilently } = useAuth0();
    useEffect(() => {
      const call = async () => {
        const token = await getAccessTokenSilently();
        const data = await get(token, 'http://localhost:8080/organizations/');
        setOrganizations(data);
      };
      call();
    }, [getAccessTokenSilently]);
    return organizations;
};

export { useOrganizations };
