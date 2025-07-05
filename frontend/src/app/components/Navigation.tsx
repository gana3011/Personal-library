'use client';

import  Link  from "next/link";
import { Button } from "@/components/ui/button";
import { useLocation } from "react-router-dom";
import { BookOpen, User, Plus, Library, Users } from "lucide-react";
import { usePathname } from "next/navigation";

const Navigation = () => {
  const pathname = usePathname();
  
  const isActive = (path: string) => pathname === path;

  return (
    <nav className="bg-white shadow-sm border-b border-gray-200 sticky top-0 z-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          <div className="flex items-center space-x-2">
            <BookOpen className="h-8 w-8 text-sky-blue" />
            <Link href="/" className="text-2xl font-bold text-almost-black hover:text-sky-blue transition-colors">
              BookTracker
            </Link>
          </div>
          
          <div className="hidden md:flex items-center space-x-6">
            <Link 
              href="/my-books" 
              className={`flex items-center space-x-2 px-3 py-2 rounded-md text-sm font-medium transition-colors ${
                isActive('/my-books') 
                  ? 'text-sky-blue bg-blue-50' 
                  : 'text-slate-gray hover:text-sky-blue hover:bg-gray-50'
              }`}
            >
              <Library className="h-4 w-4" />
              <span>My Books</span>
            </Link>
            
            <Link 
              href="/authors" 
              className={`flex items-center space-x-2 px-3 py-2 rounded-md text-sm font-medium transition-colors ${
                isActive('/authors') 
                  ? 'text-sky-blue bg-blue-50' 
                  : 'text-slate-gray hover:text-sky-blue hover:bg-gray-50'
              }`}
            >
              <Users className="h-4 w-4" />
              <span>Authors</span>
            </Link>
            
            <Link 
              href="/add-book" 
              className={`flex items-center space-x-2 px-3 py-2 rounded-md text-sm font-medium transition-colors ${
                isActive('/add-book') 
                  ? 'text-sky-blue bg-blue-50' 
                  : 'text-slate-gray hover:text-sky-blue hover:bg-gray-50'
              }`}
            >
              <Plus className="h-4 w-4" />
              <span>Add Book</span>
            </Link>
          </div>

          <div className="flex items-center space-x-4">
            <Link href="/signin">
              <Button variant="outline" className="border-sky-blue text-sky-blue hover:bg-sky-blue hover:text-white">
                Sign In
              </Button>
            </Link>
            <Link href="/signup">
              <Button className="bg-sky-blue hover:bg-blue-600 text-white">
                Sign Up
              </Button>
            </Link>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navigation;