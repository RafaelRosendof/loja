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
//import { Loader2, Package, CheckCircle, AlertCircle } from "lucide-react";
import { Loader2, Package, CheckCircle, AlertCircle, Trash2 } from "lucide-react";
import { authService } from "@/components/jwt-token";




interface DeleteProductProps {
  onDeleteSuccess?: () => void;
}

export default function DeleteProductPage({ onDeleteSuccess }: DeleteProductProps) {
  const [productId, setProductId] = useState<string>("");
  const [isLoading, setIsLoading] = useState(false);
  const [message, setMessage] = useState<{
    type: "success" | "error";
    text: string;
  } | null>(null);

  const isSubmitting = useRef(false);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = e.target;
    // Only allow numbers
    if (value === "" || /^\d+$/.test(value)) {
      setProductId(value);
    }
  };


  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (isSubmitting.current) return; // Prevent double click
    
    const id = parseInt(productId, 10);
    if (!id || id <= 0) {
      setMessage({
        type: "error",
        text: "Por favor, insira um ID válido do produto."
      });
      return;
    }

    isSubmitting.current = true;
    setIsLoading(true);
    setMessage(null);

    try {
      const resp = await authService.makeAuthenticatedRequest(
        `http://192.168.0.19:8090/produto/delete/${id}`,
        {
          method: "DELETE",
        }
      );

      if (!resp.ok) {
        const errorText = await resp.text();
        throw new Error(`Erro no servidor: ${resp.status} - ${errorText || resp.statusText}`);
      }

      // Check if response has content before parsing JSON
      const contentType = resp.headers.get("content-type");
      let responseData = null;
      if (contentType && contentType.includes("application/json")) {
        responseData = await resp.json();
      }

      setMessage({ type: "success", text: "Produto deletado com sucesso!" });
      setProductId(""); // Clear the input after successful deletion
      
      // Call the callback if provided
      if (onDeleteSuccess) {
        onDeleteSuccess();
      }

    } catch (error) {
      console.error("Erro ao deletar produto:", error);
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
    <div className="min-h-screen bg-gray-50 flex items-center justify-center p-4 font-sans">
        <Card className="w-full max-w-md shadow-lg">
            <CardHeader>
                <div className="flex items-center space-x-3 mb-2">
                    <Trash2 className="h-6 w-6 text-red-500" />
                    <CardTitle className="text-2xl font-bold text-gray-800">Deletar Produto</CardTitle>
                </div>
                <CardDescription className="text-gray-600">
                    Insira o ID do produto que você deseja remover permanentemente do sistema.
                </CardDescription>
            </CardHeader>
            <CardContent>
                {message && (
                    <Alert 
                        variant={message.type === 'error' ? 'destructive' : 'default'}
                        className={`mb-4 flex items-center p-3 rounded-lg ${message.type === "success" ? "bg-green-50 border-green-200" : "bg-red-50 border-red-200"}`}
                    >
                        {message.type === "success" ? (
                            <CheckCircle className="h-5 w-5 text-green-600 mr-3" />
                        ) : (
                            <AlertCircle className="h-5 w-5 text-red-600 mr-3" />
                        )}
                        <AlertDescription className={`font-medium ${message.type === "success" ? "text-green-800" : "text-red-800"}`}>
                            {message.text}
                        </AlertDescription>
                    </Alert>
                )}

                <form onSubmit={handleSubmit} className="space-y-6">
                    <div className="space-y-2">
                        <Label htmlFor="productId" className="font-semibold text-gray-700">
                            ID do Produto
                        </Label>
                        <Input
                            type="text" // Usar text para ter mais controle com a regex
                            id="productId"
                            placeholder="Ex: 12345"
                            value={productId}
                            onChange={handleInputChange}
                            required
                            className="w-full"
                        />
                    </div>
                    <Button type="submit" disabled={isLoading || !productId} className="w-full bg-red-600 hover:bg-red-700 text-white font-bold">
                        {isLoading ? (
                            <Loader2 className="mr-2 h-4 w-4 animate-spin" /> 
                        ) : (
                            <Trash2 className="mr-2 h-4 w-4" />
                        )}
                        {isLoading ? "Deletando..." : "Deletar Produto"}
                    </Button>
                </form>
            </CardContent>
        </Card>
    </div>
);

}