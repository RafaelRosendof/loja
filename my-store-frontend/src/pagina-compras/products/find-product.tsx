// here need to have a modal that shows the product details 
// basicly the same as the delete product page with the id and then shows the modal 

"use client";

import type React from "react";
import { useState, useRef, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { Loader2, Package, CheckCircle, AlertCircle, Search, XCircle } from "lucide-react";
import { authService } from "@/components/jwt-token";


interface FindProductProps {
  onFindSuccess?: (product: Product) => void;
}


interface Product {
  id: number;
  nome: string; 
  price: number;
  quantidade: number;
  categoria?: string;
  marca?: string;
  description?: string;
}


export default function FindProductPage({ onFindSuccess }: FindProductProps) {
  const [productId, setProductId] = useState<string>("");
  const [isLoading, setIsLoading] = useState(false);
  const [message, setMessage] = useState<{
    type: "success" | "error";
    text: string;
  } | null>(null);
  
  // Novos estados para o modal
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [foundProduct, setFoundProduct] = useState<Product | null>(null);

  const isSubmitting = useRef(false);
  
  // Efeito para fechar o modal com a tecla 'Escape'
  useEffect(() => {
    const handleKeyDown = (event: KeyboardEvent) => {
        if (event.key === 'Escape') {
            setIsModalOpen(false);
        }
    };

    window.addEventListener('keydown', handleKeyDown);
    return () => {
        window.removeEventListener('keydown', handleKeyDown);
    };
  }, []);


  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setProductId(e.target.value);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (isSubmitting.current) return;

    const id = parseInt(productId, 10);
    if (isNaN(id) || id <= 0) {
      setMessage({
        type: "error",
        text: "Por favor, insira um ID de produto numérico e válido."
      });
      return;
    }

    isSubmitting.current = true;
    setIsLoading(true);
    setMessage(null); // Limpa mensagens antigas


    try {
      const response = await authService.makeAuthenticatedRequest(
        `http://192.168.0.19:8090/produto/FindProduto/${id}`,
        {
          method: "GET",
        }
      );

      if (!response.ok) {
        if (response.status === 404) {
            throw new Error(`Produto com ID ${id} não encontrado.`);
        }
        const errorText = await response.text();
        throw new Error(`Erro no servidor: ${response.status} - ${errorText || response.statusText}`);
      }

      const productData: Product = await response.json();
      
      // Armazena os dados e abre o modal
      setFoundProduct(productData);
      setIsModalOpen(true);
      setProductId(""); // Limpa o input

      if (onFindSuccess) {
        onFindSuccess(productData);
      }

    } catch (error) {
      setMessage({
        type: "error",
        text: error instanceof Error ? error.message : "Erro desconhecido ao buscar o produto."
      });
    } finally {
      isSubmitting.current = false;
      setIsLoading(false);
    }
  };
  //Front end rendering
  return (
    <div className="min-h-screen bg-gray-50 py-12 px-4 font-sans">
      <div className="max-w-xl mx-auto">
        <Card className="shadow-lg">
          <CardHeader>
            <div className="flex items-center gap-3">
              <Search className="h-6 w-6 text-blue-600" />
              <CardTitle className="text-2xl">Buscar Produto</CardTitle>
            </div>
            <CardDescription>
              Insira o ID do produto para ver seus detalhes.
            </CardDescription>
          </CardHeader>

          <CardContent>
            <form onSubmit={handleSubmit} className="space-y-4">
              <div className="space-y-2">
                <Label htmlFor="productId">ID do Produto</Label>
                <div className="flex gap-2">
                    <Input
                        id="productId"
                        type="number"
                        value={productId}
                        onChange={handleInputChange}
                        placeholder="Digite o ID do produto"
                        disabled={isLoading}
                        className="flex-grow"
                        required
                    />
                    <Button type="submit" disabled={isLoading || !productId} className="w-40">
                        {isLoading ? (
                            <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                        ) : (
                            <Search className="mr-2 h-4 w-4" />
                        )}
                        {isLoading ? "Buscando..." : "Buscar"}
                    </Button>
                </div>
              </div>
            </form>

            {message && message.type === 'error' && (
              <Alert variant="destructive" className="mt-4">
                <AlertCircle className="h-4 w-4" />
                <AlertDescription>
                  {message.text}
                </AlertDescription>
              </Alert>
            )}
          </CardContent>
        </Card>
      </div>
      
      {/* Modal para exibir os detalhes do produto */}
      {isModalOpen && foundProduct && (
         <div 
            className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-60 backdrop-blur-sm"
            onClick={() => setIsModalOpen(false)}
        >
            <div 
                className="relative w-full max-w-lg mx-4"
                onClick={(e) => e.stopPropagation()}
            >
                <Card>
                    <CardHeader>
                        <div className="flex items-center gap-3">
                            <Package className="h-6 w-6 text-blue-600" />
                            <CardTitle>Detalhes do Produto</CardTitle>
                        </div>
                        <CardDescription>ID do Produto: {foundProduct.id}</CardDescription>
                    </CardHeader>
                    <CardContent className="space-y-3 text-sm">
                        <div className="flex justify-between border-b pb-2">
                            <span className="font-medium text-gray-600">Nome:</span>
                            <span className="text-gray-800">{foundProduct.nome}</span>
                        </div>
                        <div className="flex justify-between border-b pb-2">
                            <span className="font-medium text-gray-600">Categoria:</span>
                            <span className="text-gray-800">{foundProduct.categoria || 'N/A'}</span>
                        </div>
                        <div className="flex justify-between border-b pb-2">
                            <span className="font-medium text-gray-600">Marca:</span>
                            <span className="text-gray-800">{foundProduct.marca || 'N/A'}</span>
                        </div>
                        <div className="flex justify-between border-b pb-2">
                            <span className="font-medium text-gray-600">Preço:</span>
                            <span className="font-semibold text-green-600">R$ {foundProduct.price.toFixed(2)}</span>
                        </div>
                        <div className="flex justify-between">
                            <span className="font-medium text-gray-600">Quantidade em Estoque:</span>
                            <span className="text-gray-800">{foundProduct.quantidade}</span>
                        </div>
                    </CardContent>
                    <CardFooter className="flex justify-end">
                        <Button variant="outline" onClick={() => setIsModalOpen(false)}>
                            Fechar
                        </Button>
                    </CardFooter>
                    <Button 
                        type="button"
                        variant="ghost"
                        size="icon"
                        className="absolute top-3 right-3 rounded-full h-8 w-8"
                        onClick={() => setIsModalOpen(false)}
                    >
                        <XCircle className="h-5 w-5 text-gray-500" />
                    </Button>
                </Card>
            </div>
        </div>
      )}
    </div>
  );
}
