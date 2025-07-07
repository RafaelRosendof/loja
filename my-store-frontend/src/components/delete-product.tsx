"use client";

import type React from "react";
import { useState, useRef } from "react";
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
import { authService } from "@/components/jwt-token";



interface DeleteProductProps {
  productId: number;
  onDeleteSuccess: () => void;
}


export default function DeleteProductPage() {
    const [productId, setProductId] = useState<number>({
    id: 0,
  });
    const [isLoading, setIsLoading] = useState(false);
    const [message, setMessage] = useState<{
    type: "success" | "error";
    text: string;
    } | null>(null);



    const isSubmitting = useRef(false);

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
      const { value } = e.target;
      setProductId(Number.parseInt(value, 10) || 0);
    };


    const handleSubmit = async (e: React.FormEvent) => {
      e.preventDefault();
      if (isSubmitting.current) return; // Previne clique duplo
      isSubmitting.current = true;
      setIsLoading(true);
      setMessage(null);

      try{
        const resp = await authService.makeAuthenticatedRequest(
            "http://192.168.0.19:8090/produto/delete/"+productId,
            {
              method: "DELETE",
            }
        );

        if (!resp.ok){
            const errorText = await resp.text();
        throw new Error(`Erro no servidor: ${resp.status} - ${errorText || resp.statusText}`);
        }

        await resp.json();

        setMessage({ type: "success", text: "Produto deletado com sucesso!" });
    
    } catch (error) {
        console.error("Erro ao adicionar produto:", error);
        setMessage({
        type: "error",
        text: error instanceof Error ? error.message : "Ocorreu um erro desconhecido",
      });
    } finally {
        isSubmitting.current = false;
        setIsLoading(false);
    }
};


return (
    
)