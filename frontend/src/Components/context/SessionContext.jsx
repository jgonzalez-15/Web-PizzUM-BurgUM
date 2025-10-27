// SessionContext.js
import { createContext, useState } from 'react';

export const SessionContext = createContext();

export const SessionProvider = ({ children }) => {
  const [sessionType, setSessionType] = useState("Guest");
  const [sessionInfo, setSessionInfo] = useState({});

  return (
    <SessionContext.Provider value={{ sessionType, setSessionType, sessionInfo, setSessionInfo }}>
      {children}
    </SessionContext.Provider>
  );
};