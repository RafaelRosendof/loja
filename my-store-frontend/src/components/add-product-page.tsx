"use client";

import type React from "react";

import { useState } from "react";
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
    setIsLoading(true);
    setMessage(null);

    const handleJwt = async () => {
      try {
        const resp = await fetch("http://192.168.0.19:8090/login", {
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
          throw new Error("Erro ao obter token de autenticação");
        }

        const data = await resp.json();
        localStorage.setItem("jwt_token", data.token);
        alert("Login realizado com sucesso!");
        return data.token;
      } catch (error) {
        console.error(error);
        alert("Erro ao realizar login");
      }
    };

    try {
      // Get JWT token from localStorage (adjust based on your storage method)
      //const token = localStorage.getItem("jwt_token");
      //const token = localStorage.getItem("jwt_token");
      const token = localStorage.getItem("jwt_token") || (await handleJwt());

      if (!token) {
        throw new Error("Token de autenticação não encontrado");
      }

      const response = await fetch(
        "http://192.168.0.19:8090/produto/adicionarProduto",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
          },
          body: JSON.stringify(formData),
          credentials: "include",
        },
      );

      if (!response.ok) {
        throw new Error(`Erro ${response.status}: ${response.statusText}`);
      }

      const result = await response.json();

      setMessage({ type: "success", text: "Produto adicionado com sucesso!" });

      // Reset form
      setFormData({
        id: 0,
        nome: "",
        categoria: "",
        price: 0,
        quantidade: 0,
        marca: "",
      });
    } catch (error) {
      console.error("Erro ao adicionar produto:", error, "{error.statusText}");
      setMessage({
        type: "error",
        text:
          error instanceof Error ? error.message : "Erro ao adicionar produto",
      });
    } finally {
      setIsLoading(false);
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
