import { createContext } from "react";

export const UserContext = createContext({
    userInfos: [],
    reloadUserInfos: () => {},
    isAllowedToEdit: () => {},
    isAllowedToAdmin: () => {}
  }
);