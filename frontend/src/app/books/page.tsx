'use client'

import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import Navigation from "@/app/components/Navigation";
import { Search, Filter, BookOpen, Star, Calendar, User, Plus } from "lucide-react";
import Link  from "next/link";
import { useBook } from "../contexts/BookContext";

const MyBooks = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const [filterStatus, setFilterStatus] = useState("all");
  const {books, isLoading} = useBook();

  useEffect(()=>{
    console.log(books)
  },[books, isLoading]);

  const dummybooks = [
    {
      id: 1,
      title: "The Great Gatsby",
      author: "F. Scott Fitzgerald",
      cover: "/placeholder.svg",
      status: "read",
      rating: 4,
      genre: "Fiction",
      pages: 180,
      dateAdded: "2024-01-15",
      notes: "A masterpiece of American literature. The symbolism is incredible."
    },
    {
      id: 2,
      title: "To Kill a Mockingbird",
      author: "Harper Lee",
      cover: "/placeholder.svg",
      status: "currently-reading",
      rating: null,
      genre: "Fiction",
      pages: 376,
      dateAdded: "2024-02-01",
      notes: "Currently on chapter 5. Really enjoying the narrative style."
    },
    {
      id: 3,
      title: "1984",
      author: "George Orwell",
      cover: "/placeholder.svg",
      status: "want-to-read",
      rating: null,
      genre: "Dystopian Fiction",
      pages: 328,
      dateAdded: "2024-02-10",
      notes: "Recommended by a friend. Looking forward to reading this classic."
    },
    {
      id: 4,
      title: "The Catcher in the Rye",
      author: "J.D. Salinger",
      cover: "/placeholder.svg",
      status: "read",
      rating: 5,
      genre: "Fiction",
      pages: 277,
      dateAdded: "2024-01-20",
      notes: "Holden's voice is so authentic. This book really resonated with me."
    }
  ];

  const getStatusColor = (status: string) => {
    switch (status) {
      case "read":
        return "bg-green-100 text-green-800";
      case "currently-reading":
        return "bg-blue-100 text-blue-800";
      case "want-to-read":
        return "bg-yellow-100 text-yellow-800";
      default:
        return "bg-gray-100 text-gray-800";
    }
  };

  const getStatusText = (status: string) => {
    switch (status) {
      case "read":
        return "Read";
      case "currently-reading":
        return "Currently Reading";
      case "want-to-read":
        return "Want to Read";
      default:
        return status;
    }
  };

  const renderStars = (rating: number | null) => {
    if (!rating) return <span className="text-slate-gray">Not rated</span>;
    return (
      <div className="flex items-center">
        {[...Array(5)].map((_, i) => (
          <Star
            key={i}
            className={`h-4 w-4 ${
              i < rating ? "text-yellow-400 fill-current" : "text-gray-300"
            }`}
          />
        ))}
      </div>
    );
  };

  const filteredBooks = books.filter(book => {
    const matchesSearch = book.book.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         book.author.name.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesStatus = filterStatus === "all" || book.status === filterStatus;
    return matchesSearch && matchesStatus;
  });

  // useEffect(()=>{
  //     const fetch = async() =>{
  //       try{
  //         await fetchBooks();
  //       }
  //       catch(error){
  //         console.log(error);
  //       }
  //     }
  //     fetch();
  //   },[])

  return (
    <div className="min-h-screen bg-soft-white">
      <Navigation />
      
      <div className="max-w-7xl mx-auto py-8 px-4 sm:px-6 lg:px-8">
        {/* Header */}
        <div className="flex flex-col md:flex-row justify-between items-start md:items-center mb-8">
          <div>
            <h1 className="text-3xl font-bold text-almost-black mb-2">My Books</h1>
            <p className="text-slate-gray">Manage your personal library</p>
          </div>
          <Link href="/add-book">
            <Button className="bg-sky-blue hover:bg-blue-600 text-white mt-4 md:mt-0">
              <Plus className="h-4 w-4 mr-2" />
              Add Book
            </Button>
          </Link>
        </div>

        {/* Search and Filter */}
        <div className="flex flex-col md:flex-row gap-4 mb-6">
          <div className="relative flex-1">
            <Search className="absolute left-3 top-3 h-4 w-4 text-slate-gray" />
            <Input
              placeholder="Search books
           or authors..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="pl-10 border-gray-300 focus:border-sky-blue focus:ring-sky-blue"
            />
          </div>
          <Select value={filterStatus} onValueChange={setFilterStatus}>
            <SelectTrigger className="w-full md:w-48 border-gray-300 focus:border-sky-blue focus:ring-sky-blue">
              <Filter className="h-4 w-4 mr-2" />
              <SelectValue />
            </SelectTrigger>
            <SelectContent className="bg-white border border-gray-200 shadow-lg">
              <SelectItem value="all">All Books</SelectItem>
              <SelectItem value="read">Read</SelectItem>
              <SelectItem value="currently-reading">Currently Reading</SelectItem>
              <SelectItem value="want-to-read">Want to Read</SelectItem>
            </SelectContent>
          </Select>
        </div>

        {/* Books Grid */}
        {isLoading && <div>Loading books...</div>}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {books.map((book) => (
            <Card key={book.id} className="hover:shadow-lg transition-shadow">
              <CardHeader className="pb-4">
                <div className="flex items-start space-x-4">
                  <div className="w-16 h-20 bg-gray-200 rounded-md flex items-center justify-center">
                    <BookOpen className="h-8 w-8 text-slate-gray" />
                  </div>
                  <div className="flex-1">
                    <CardTitle className="text-lg text-almost-black leading-tight">
                      {book.book.name}
                    </CardTitle>
                    <CardDescription className="flex items-center mt-1">
                      <User className="h-3 w-3 mr-1" />
                      {book.author.name}
                    </CardDescription>
                    <div className="mt-2">
                      <Badge className={getStatusColor(book.status)}>
                        {getStatusText(book.status)}
                      </Badge>
                    </div>
                  </div>
                </div>
              </CardHeader>
              
              <CardContent className="pt-0">
                <div className="space-y-3">   
                  <div className="flex justify-between text-sm">
                    <span className="text-slate-gray">Genre:</span>
                    <span className="text-almost-black">{book.genre}</span>
                  </div>
                  
                  <div className="flex justify-between text-sm">
                    <span className="text-slate-gray">Pages:</span>
                    <span className="text-almost-black">{book.pages}</span>
                  </div>
                  
                  <div className="flex justify-between text-sm">
                    <span className="text-slate-gray">Added:</span>
                    <span className="text-almost-black flex items-center">
                      <Calendar className="h-3 w-3 mr-1" />
                      {new Date(book.dateAdded).toLocaleDateString()}
                    </span>
                  </div>
                  
                  {book.notes && (
                    <div className="pt-2 border-t border-gray-200">
                      <p className="text-sm text-slate-gray italic">
                        "{book.notes}"
                      </p>
                    </div>
                  )}
                  
                  <div className="flex gap-2 pt-2">
                    <Button variant="outline" size="sm" className="flex-1">
                      Edit
                    </Button>
                    <Button variant="outline" size="sm" className="text-coral border-coral hover:bg-coral hover:text-white">
                      Delete
                    </Button>
                  </div>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>

        {filteredBooks.length === 0 && (
          <div className="text-center py-12">
            <BookOpen className="h-16 w-16 text-slate-gray mx-auto mb-4" />
            <h3 className="text-xl font-semibold text-almost-black mb-2">No books
           found</h3>
            <p className="text-slate-gray mb-4">
              {searchTerm || filterStatus !== "all"
                ? "Try adjusting your search or filter criteria"
                : "Start building your library by adding your first book"}
            </p>
            <Link href="/add-book">
              <Button className="bg-sky-blue hover:bg-blue-600 text-white">
                <Plus className="h-4 w-4 mr-2" />
                Add Your First Book
              </Button>
            </Link>
          </div>
        )}
      </div>
    </div>
  );
};

export default MyBooks;