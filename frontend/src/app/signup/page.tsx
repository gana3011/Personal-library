'use client'
import Link from "next/link";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import Navigation from "@/app/components/Navigation";
import { BookOpen } from "lucide-react";
import { useState } from "react";
import { useRouter } from "next/navigation";
import { useAuth } from "../contexts/AuthContext";
import { useToast } from "@/hooks/use-toast";


const SignUp = () => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const {signUp, isLoading} = useAuth();
  const {toast} = useToast();
  const router = useRouter();

  const handleSumbit = async (e:React.FormEvent) =>{
    
    e.preventDefault();
    if(password !== confirmPassword){
      toast({
        title: "Passwords don't match",
        description: "Please make sure your passwords match.",
        variant: "destructive",
      });
      return;
    }
    try{
      await signUp(name, email, password);
      router.push("/signin");
    } catch(err: any){
      toast({
        title: "Sign up failed",
        description: "Can't create a account, please try again after some time.",
        variant: "destructive",
      });
    }
  }

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
              Create Account
            </CardTitle>
            <CardDescription className="text-slate-gray">
              Join BookTracker and start organizing your reading journey
            </CardDescription>
          </CardHeader>
          
          <CardContent className="space-y-6">
            <form className="space-y-4" onSubmit={handleSumbit}>
              <div className="grid gap-4">
                <div className="space-y-2">
                  <Label htmlFor="name" className="text-almost-black">Name</Label>
                  <Input
                    id="name"
                    type="text"
                    placeholder="John"
                    value={name}
                    onChange={(e)=>setName(e.target.value)}
                    className="border-gray-300 focus:border-sky-blue focus:ring-sky-blue"
                    required
                  />
                </div>
              </div>
              
              <div className="space-y-2">
                <Label htmlFor="email" className="text-almost-black">Email</Label>
                <Input
                  id="email"
                  type="email"
                  placeholder="john@example.com"
                  value={email}
                  onChange={(e)=>setEmail(e.target.value)}
                  className="border-gray-300 focus:border-sky-blue focus:ring-sky-blue"
                  required
                />
              </div>
              
              <div className="space-y-2">
                <Label htmlFor="password" className="text-almost-black">Password</Label>
                <Input
                  id="password"
                  type="password"
                  placeholder="Create a password"
                  value={password}
                  onChange={(e)=>setPassword(e.target.value)}
                  className="border-gray-300 focus:border-sky-blue focus:ring-sky-blue"
                  required
                />
              </div>
              
              <div className="space-y-2">
                <Label htmlFor="confirmPassword" className="text-almost-black">Confirm Password</Label>
                <Input
                  id="confirmPassword"
                  type="password"
                  placeholder="Confirm your password"
                  value={confirmPassword}
                  onChange={(e)=>setConfirmPassword(e.target.value)}
                  className="border-gray-300 focus:border-sky-blue focus:ring-sky-blue"
                  required
                />
              </div>
              <Button
                type="submit"
                className="w-full bg-sky-blue hover:bg-blue-600 text-white"
                disabled={isLoading}
              >
              {isLoading ? 'Please Wait...': 'Create Account'}
              </Button>
            </form>
            
            <div className="text-center">
              <p className="text-slate-gray">
                Already have an account?{" "}
                <Link href="/signin" className="text-sky-blue hover:underline font-medium">
                  Sign in
                </Link>
              </p>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default SignUp;