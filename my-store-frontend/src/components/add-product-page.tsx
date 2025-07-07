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

interface ProductData {
  id: number;
  nome: string;
  categoria: string;
  price: number;
  quantidade: number;
  marca: string;
}

export default function AddProductPage() {
  const [formData, setFormData] = useState<ProductData>({
    id: 0,
    nome: "",
    categoria: "",
    price: 0,
    quantidade: 0,
    marca: "",
  });

  const [isLoading, setIsLoading] = useState(false);
  const [message, setMessage] = useState<{
    type: "success" | "error";
    text: string;
  } | null>(null);

  // Usar uma referência para evitar envios múltiplos
  const isSubmitting = useRef(false);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]:
        name === "price" || name === "quantidade"
          ? Number.parseFloat(value) || 0
          : value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (isSubmitting.current) return; // Previne clique duplo

    isSubmitting.current = true;
    setIsLoading(true);
    setMessage(null);

    try {
      // 3. Use o authService para fazer a requisição!
      const response = await authService.makeAuthenticatedRequest(
        "http://192.168.0.19:8090/produto/adicionarProduto",
        {
          method: "POST",
          body: JSON.stringify(formData),
        }
      );

      if (!response.ok) {
        // A sua classe AuthService já tentou renovar o token se deu 401.
        // Se ainda não deu certo, o erro é outro.
        const errorText = await response.text();
        throw new Error(`Erro no servidor: ${response.status} - ${errorText || response.statusText}`);
      }

      await response.json();

      setMessage({ type: "success", text: "Produto adicionado com sucesso!" });

      // Resetar o formulário
      setFormData({
        id: 0,
        nome: "",
        categoria: "",
        price: 0,
        quantidade: 0,
        marca: "",
      });

    } catch (error) {
      console.error("Erro ao adicionar produto:", error);
      setMessage({
        type: "error",
        text: error instanceof Error ? error.message : "Ocorreu um erro desconhecido",
      });
    } finally {
      setIsLoading(false);
      isSubmitting.current = false;
    }
  };
  


  const isFormValid =
    formData.nome &&
    formData.categoria &&
    formData.marca &&
    formData.price > 0 &&
    formData.quantidade > 0;

  return (
    <div className="min-h-screen bg-gray-50 py-8 px-4">
      <div className="max-w-2xl mx-auto">
        <Card>
          <CardHeader>
            <div className="flex items-center gap-2">
              <Package className="h-6 w-6 text-blue-600" />
              <CardTitle className="text-2xl">Adicionar Produto</CardTitle>
            </div>
            <CardDescription>
              Preencha os dados do produto para adicionar ao estoque
            </CardDescription>
          </CardHeader>

          <CardContent>
            {message && (
              <Alert
                className={`mb-6 ${message.type === "success" ? "border-green-200 bg-green-50" : "border-red-200 bg-red-50"}`}
              >
                {message.type === "success" ? (
                  <CheckCircle className="h-4 w-4 text-green-600" />
                ) : (
                  <AlertCircle className="h-4 w-4 text-red-600" />
                )}
                <AlertDescription
                  className={
                    message.type === "success"
                      ? "text-green-800"
                      : "text-red-800"
                  }
                >
                  {message.text}
                </AlertDescription>
              </Alert>
            )}

            <form onSubmit={handleSubmit} className="space-y-6">
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="space-y-2">
                  <Label htmlFor="nome">Nome do Produto *</Label>
                  <Input
                    id="nome"
                    name="nome"
                    type="text"
                    value={formData.nome}
                    onChange={handleInputChange}
                    placeholder="Ex: Notebook Dell Inspiron 15"
                    required
                    className="w-full"
                  />
                </div>

                <div className="space-y-2">
                  <Label htmlFor="marca">Marca *</Label>
                  <Input
                    id="marca"
                    name="marca"
                    type="text"
                    value={formData.marca}
                    onChange={handleInputChange}
                    placeholder="Ex: Dell"
                    required
                    className="w-full"
                  />
                </div>
              </div>

              <div className="space-y-2">
                <Label htmlFor="categoria">Categoria *</Label>
                <Input
                  id="categoria"
                  name="categoria"
                  type="text"
                  value={formData.categoria}
                  onChange={handleInputChange}
                  placeholder="Ex: Informática"
                  required
                  className="w-full"
                />
              </div>

              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="space-y-2">
                  <Label htmlFor="price">Preço (R$) *</Label>
                  <Input
                    id="price"
                    name="price"
                    type="number"
                    step="0.01"
                    min="0"
                    value={formData.price || ""}
                    onChange={handleInputChange}
                    placeholder="0.00"
                    required
                    className="w-full"
                  />
                </div>

                <div className="space-y-2">
                  <Label htmlFor="quantidade">Quantidade *</Label>
                  <Input
                    id="quantidade"
                    name="quantidade"
                    type="number"
                    min="0"
                    value={formData.quantidade || ""}
                    onChange={handleInputChange}
                    placeholder="0"
                    required
                    className="w-full"
                  />
                </div>
              </div>

              <div className="flex gap-4 pt-4">
                <Button
                  type="submit"
                  disabled={!isFormValid || isLoading}
                  className="flex-1 bg-blue-600 hover:bg-blue-700"
                >
                  {isLoading ? (
                    <>
                      <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                      Adicionando...
                    </>
                  ) : (
                    "Adicionar Produto"
                  )}
                </Button>

                <Button
                  type="button"
                  variant="outline"
                  onClick={() => {
                    setFormData({
                      id: 0,
                      nome: "",
                      categoria: "",
                      price: 0,
                      quantidade: 0,
                      marca: "",
                    });
                    setMessage(null);
                  }}
                  disabled={isLoading}
                >
                  Limpar
                </Button>
              </div>
            </form>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
