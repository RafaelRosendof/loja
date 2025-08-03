import React, { useState, useRef } from "react";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";

import { Alert, AlertDescription } from "@/components/ui/alert";
import { Loader2, Package, CheckCircle, AlertCircle } from "lucide-react";



// Class to handle the JWT token logic
class AuthService {

    private token: string | null = null;
    private isLoading: boolean = false;
    private loginPromise: Promise<string> | null = null;


    async getValidToken(): Promise<string> {
        if (this.token && this.isTokenValid(this.token)){
            return this.token;
        }
        // If we are already trying to log in, wait for that promise to resolve
        const storedToken = null;

        if (storedToken && this.isTokenValid(storedToken)){
            this.token = storedToken;
            return this.token;
        }

        // if no valid token, authenticate 

        return await this.authenticate();
    }


    private async authenticate(): Promise<string> {
        // to prevent multiple login attempts
        if (this.loginPromise) {
            return this.loginPromise;
        }

        this.loginPromise = this.performLogin();

        // try to get a new token
        try{
            const token = await this.loginPromise;
            return token;

        } finally {
            this.loginPromise = null;
        }
    }

    private async performLogin(): Promise<string> {

        try{
            const resp = await fetch("http://192.168.0.19:8090/login" , {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    username: "figas",
                    password: "1234",
                }),
                credentials: "include",
            });

            if (!resp.ok) {
                throw new Error(`Login failed  ${resp.status} ${resp.statusText}`);
            } 

            const data = await resp.json();
            this.token = data.token;

            console.log("Login successful, token received:", this.token);

            return this.token;
        
        } catch (error) {
            console.error("Error during login:", error);
            throw new Error("Failed to authenticate. Please check your credentials.");
        }
    }

    private isTokenValid(token: string): boolean {
        // do some basic validation

        if (!token) return false;

        try{
            const payload = JSON.parse(atob(token.split('.')[1]));
            const timeNow = Date.now() / 1000;

            return payload.exp > (timeNow + 300); // 5 minutes buffer

        } catch (error) {
            console.error("Invalid token format:", error);
            return false;
        }
    }


    clearToken(): void {
        this.token = null;
    }


    async makeAuthenticatedRequest(url: string , options: RequestInit = {}): Promise<Response> {
        const token = await this.getValidToken();

        const authenticatedOptions: RequestInit = {
            ...options,
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json",     
            },
            credentials: "include",
        };

        const response = await fetch(url, authenticatedOptions);
    
        if (response.status === 401) {
            console.log("Token expired or invalid, refreshing...");
            this.clearToken();
            
            const newToken = await this.getValidToken();
            authenticatedOptions.headers = {
                ...authenticatedOptions.headers,
                "Authorization": `Bearer ${newToken}`,
            };

            return fetch(url, authenticatedOptions);    
        }
    
        return response;
    }
}

export const authService = new AuthService();