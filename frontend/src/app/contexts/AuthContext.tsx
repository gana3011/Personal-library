'use client';

import { ADD_USER, LOGIN_USER } from "@/graphql/mutations";
import { useMutation } from "@apollo/client";
import { createContext, useContext, useEffect, useState } from "react";

interface User{
    id: String,
    name: String
}

interface AuthContextType {
    user: User | null;
    signIn: (email:string, password:string) => Promise<void>
    signUp: (name: string, email: string, password: string) => Promise<Boolean>;
    // signOut: () => void;
    loading: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [user, setUser] = useState<User | null>(null);
  const [addUser, {loading: addUserLoading}] = useMutation(ADD_USER);
  const [loginUser, {loading: loginLoading}] = useMutation(LOGIN_USER);
  const loading = loginLoading || addUserLoading;

  useEffect(() => {
  const token = localStorage.getItem('authToken');
  const userData = localStorage.getItem('user');

  if (token && userData) {
    try {
      const parsedUser = JSON.parse(userData);
      setUser(parsedUser);
    } catch (err) {
      localStorage.removeItem('user');
      localStorage.removeItem('authToken');
    }
  }
}, []);

const signUp = async (name: String, email: String, password: String)=>{
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
}

const signIn = async (email: String, password: String) => {
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
      console.log(user);
      if(user){
        localStorage.setItem('accessToken', user.access);
        localStorage.setItem('userId',user.id);
        setUser(user);
      }
    }
    catch(error){
        throw error;
    }
}

return (
    <AuthContext.Provider value={{user,loading,signIn, signUp }}>
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