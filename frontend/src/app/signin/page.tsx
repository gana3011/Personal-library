'use client'

import Link from "next/link";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import Navigation from "@/app/components/Navigation";
import { BookOpen } from "lucide-react";

const SignIn = () => {
  return (
    <div className="min-h-screen bg-soft-white">
      <Navigation />
      
      <div className="flex items-center justify-center min-h-[calc(100vh-4rem)] py-12 px-4 sm:px-6 lg:px-8">
        <Card className="w-full max-w-md">
          <CardHeader className="text-center">
            <div className="flex items-center justify-center mb-4">
              <BookOpen className="h-10 w-10 text-sky-blue" />
            </div>
            <CardTitle className="text-2xl font-bold text-almost-black">
              Welcome Back
            </CardTitle>
            <CardDescription className="text-slate-gray">
              Sign in to your BookTracker account
            </CardDescription>
          </CardHeader>
          
          <CardContent className="space-y-6">
            <form className="space-y-4">
              <div className="space-y-2">
                <Label htmlFor="email" className="text-almost-black">Email</Label>
                <Input
                  id="email"
                  type="email"
                  placeholder="Enter your email"
                  className="border-gray-300 focus:border-sky-blue focus:ring-sky-blue"
                />
              </div>
              
              <div className="space-y-2">
                <Label htmlFor="password" className="text-almost-black">Password</Label>
                <Input
                  id="password"
                  type="password"
                  placeholder="Enter your password"
                  className="border-gray-300 focus:border-sky-blue focus:ring-sky-blue"
                />
              </div>
              
              <div className="flex items-center justify-between">
                <Link href="/forgot-password" className="text-sm text-sky-blue hover:underline">
                  Forgot password?
                </Link>
              </div>
              
              <Button
                type="submit"
                className="w-full bg-sky-blue hover:bg-blue-600 text-white"
              >
                Sign In
              </Button>
            </form>
            
            <div className="text-center">
              <p className="text-slate-gray">
                Don't have an account?{" "}
                <Link href="/signup" className="text-sky-blue hover:underline font-medium">
                  Sign up
                </Link>
              </p>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default SignIn;
