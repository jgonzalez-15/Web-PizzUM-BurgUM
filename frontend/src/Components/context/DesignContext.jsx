import { createContext } from "react";

export const DesignContext = createContext({
    glutenFreeOnly: false,
    setGlutenFreeOnly: () => {},
});