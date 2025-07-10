'use client';

import { ADD_USER, LOGIN_USER } from "@/graphql/mutations";
import { useMutation } from "@apollo/client";
import { createContext, useContext, useEffect, useState } from "react";

interface User{
    id: String,
    name: String,
    access: String
}

interface AuthContextType {
    user: User | null;
    signIn: (email:string, password:string) => Promise<void>
    signUp: (name: string, email: string, password: string) => Promise<Boolean>;
    // signOut: () => void;
    isLoading: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [user, setUser] = useState<User | null>(null);
  const [addUser, {loading: addUserLoading}] = useMutation(ADD_USER);
  const [loginUser, {loading: loginLoading}] = useMutation(LOGIN_USER);
  const [isLoading, setIsLoading] = useState(true);
  useEffect(() => {
  const token = localStorage.getItem('accessToken');
  const userData = localStorage.getItem('user');

  if (token && userData) {
    try {
      const parsedUser = JSON.parse(userData);
      // console.log(parsedUser);
      setUser(parsedUser);
    } catch (err) {
      localStorage.removeItem('user');
      localStorage.removeItem('accessToken');
    }
    setIsLoading(false);
  }
}, []);

useEffect(()=>{

})

const signUp = async (name: String, email: String, password: String)=>{
  setIsLoading(true);
    try{
        await addUser({
        variables: {
          userInput: {
            name: name,
            email: email, 
            password: password
          },
        },
      });
      return true;
    }
    catch(error){
        throw error;
    }
    finally{
      setIsLoading(false);
    }
}

const signIn = async (email: String, password: String) => {
  setIsLoading(true);
    try{
        const {data} = await loginUser({
        variables : {
          userInput:{
            email: email,
            password: password,
          },
        },
      });
      const user = data.loginUser;
      if(user){
        localStorage.setItem('accessToken', user.access);
        localStorage.setItem('user', JSON.stringify(user));
        setUser(user);
      }
    }
    catch(error){
        throw error;
    }
    finally{
      setIsLoading(false);
    }
}

return (
    <AuthContext.Provider value={{user,isLoading,signIn, signUp }}>
      {children}
    </AuthContext.Provider>
);
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}