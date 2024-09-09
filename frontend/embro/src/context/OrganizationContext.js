import { createContext } from "react";

export const OrganizationContext = createContext(
    {
        organizations: [],
        setOrganizations: () => {},
        currentOrganization: null,
        setCurrentOrganization: () => {}
    }
);