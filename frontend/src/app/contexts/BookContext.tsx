'use client'

import { createContext, use, useContext, useEffect, useState } from "react";
import { useAuth } from "./AuthContext";
import { useLazyQuery, useMutation, useQuery } from "@apollo/client";
import { FETCH_BOOKS } from "@/graphql/queries";

export interface Book {
  book: {
    name: string;
  };
  author: {
    name: string;
  };
  status: string;
}


interface BookContextType {
    books: Book[],
    // addBook: (book: Omit<Book, 'id'|'userId'>) => Promise<void>;
    fetchBooks: ()=> Promise<void>
    isLoading: boolean
}

const BookContext = createContext<BookContextType | undefined>(undefined);

export function BookProvider({children} : {children: React.ReactNode}){
    const {user} = useAuth();
    const [books, setBooks] = useState<Book[]>([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [fetchBooksByUserId, {loading}] = useLazyQuery(FETCH_BOOKS);

//     const filteredBooks = books.filter(book => {
//     const matchesSearch = book.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
//                          book.author.toLowerCase().includes(searchTerm.toLowerCase());
//     const matchesStatus = filterStatus === "all" || book.status === filterStatus;
//     return matchesSearch && matchesStatus;
//   });
    const fetchBooks = async () => {
        console.log("fetching...");
        console.log(user);
        if(!user) return;
        setIsLoading(true);
        try{
            const token = localStorage.getItem('accessToken');
            console.log(`token:${token}`);
            const result = await fetchBooksByUserId({
                variables:{
                    userId: user.id
                },
                context: {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            });
             console.log(result.data.fetchBooksByUserId);
        }
        catch(error){
            throw error;
        }
        finally{
            setIsLoading(false);
        }
    };

    //wait for the user to load in AuthProvider before calling the fetchBooks(), important!!! remember nigga
    useEffect(()=>{
        if(user){
            fetchBooks();
        }
    },[user]);
    return <BookContext.Provider value={{books,fetchBooks,isLoading}}>{children}</BookContext.Provider>
}

export function useBook(){
    const context = useContext(BookContext);
    if(context == undefined){
        throw new Error('useBook must be used within a BookProvider');
    }
    return context;
}