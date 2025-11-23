import { createContext } from "react";

export const ContextoDisenio = createContext({
    soloSinGluten: false,
    setSoloSinGluten: () => {},
});
