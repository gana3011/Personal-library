'use client';
import Link from 'next/link';
import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import Navigation from "@/app/components/Navigation";
import { BookOpen, Users, TrendingUp} from "lucide-react";
import { useEffect } from 'react';
import { useAuth } from './contexts/AuthContext';
import { useRouter } from 'next/navigation';

const Index = () => {
  const{user, isLoading} = useAuth();
  const router = useRouter();

  useEffect(()=>{
    if(!isLoading && user){
      router.push("/books");
    }
  },[user, isLoading])
  return (
    <div className="min-h-screen bg-soft-white">
      <Navigation />
      
      {/* Hero Section */}
      <section className="bg-gradient-to-br from-soft-white to-blue-50 py-20">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center">
            <h1 className="text-5xl md:text-6xl font-bold text-almost-black mb-6">
              Track Your Reading
              <span className="text-sky-blue block">Journey</span>
            </h1>
            <p className="text-xl text-slate-gray mb-8 max-w-2xl mx-auto">
              Organize your books, discover new authors, and keep track of your reading progress with our beautiful and intuitive book tracking platform.
            </p>
            <div className="flex flex-col sm:flex-row gap-4 justify-center">
              <Link href ="/signup">
                <Button size="lg" className="bg-sky-blue hover:bg-blue-600 text-white px-8 py-3 text-lg">
                  Get Started Free
                </Button>
              </Link>
              <Link href="/signin">
                <Button size="lg" variant="outline" className="border-sky-blue text-sky-blue hover:bg-sky-blue hover:text-white px-8 py-3 text-lg">
                  Sign In
                </Button>
              </Link>
            </div>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="py-16 bg-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-12">
            <h2 className="text-3xl font-bold text-almost-black mb-4">
              Everything You Need to Track Your Books
            </h2>
            <p className="text-lg text-slate-gray max-w-2xl mx-auto">
              From organizing your personal library to discovering new authors, we've got you covered.
            </p>
          </div>
          
          <div className="grid md:grid-cols-3 gap-8">
            <Card className="border-gray-200 hover:shadow-lg transition-shadow">
              <CardContent className="p-6 text-center">
                <div className="bg-blue-50 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
                  <BookOpen className="h-8 w-8 text-sky-blue" />
                </div>
                <h3 className="text-xl font-semibold text-almost-black mb-2">
                  Organize Your Library
                </h3>
                <p className="text-slate-gray">
                  Keep track of all your books in one place. Add books you've read, are reading, or want to read.
                </p>
              </CardContent>
            </Card>

            <Card className="border-gray-200 hover:shadow-lg transition-shadow">
              <CardContent className="p-6 text-center">
                <div className="bg-coral/10 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
                  <Users className="h-8 w-8 text-coral" />
                </div>
                <h3 className="text-xl font-semibold text-almost-black mb-2">
                  Discover Authors
                </h3>
                <p className="text-slate-gray">
                  Explore your favorite authors and discover new ones based on your reading history.
                </p>
              </CardContent>
            </Card>

            <Card className="border-gray-200 hover:shadow-lg transition-shadow">
              <CardContent className="p-6 text-center">
                <div className="bg-green-50 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
                  <TrendingUp className="h-8 w-8 text-green-600" />
                </div>
                <h3 className="text-xl font-semibold text-almost-black mb-2">
                  Track Progress
                </h3>
                <p className="text-slate-gray">
                  Monitor your reading habits and see your progress over time with beautiful visualizations.
                </p>
              </CardContent>
            </Card>
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="py-10 bg-gradient-to-r from-sky-blue to-blue-600">
        <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
          <h2 className="text-3xl font-bold text-white mb-4">
            Ready to Start Your Reading Journey?
          </h2>
          <Link href="/signup">
            <Button size="lg" className="bg-white text-sky-blue hover:bg-gray-100 px-8 py-3 text-lg">
              Sign Up Now
            </Button>
          </Link>
        </div>
      </section>
    </div>
  );
};

export default Index;