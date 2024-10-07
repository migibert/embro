import { createContext } from "react";

export const OrganizationContext = createContext(
    {
        currentOrganization: null,
        setCurrentOrganization: () => {}
    }
);