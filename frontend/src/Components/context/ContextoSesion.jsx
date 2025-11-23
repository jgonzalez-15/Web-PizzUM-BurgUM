import { createContext, useState } from 'react';

export const ContextoSesion = createContext();

export const SessionProvider = ({ children }) => {
  const [sessionType, setSessionType] = useState("INVITADO");
  const [sessionInfo, setSessionInfo] = useState({});

  return (
    <ContextoSesion.Provider value={{ sessionType, setSessionType, sessionInfo, setSessionInfo }}>
      {children}
    </ContextoSesion.Provider>
  );
};