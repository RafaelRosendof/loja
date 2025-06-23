//revisar esse cara aqui 

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

interface ProductData {
  id: number;
  nome: string;
  categoria: string;
  price: number;
  quantidade: number;
  marca: string;
}

// Auth service to handle JWT tokens properly
class AuthService {
  private token: string | null = null;
  private isAuthenticating: boolean = false;
  private loginPromise: Promise<string> | null = null;

  async getValidToken(): Promise<string> {
    // If we have a valid token in memory, use it
    if (this.token && this.isTokenValid(this.token)) {
      return this.token;
    }

    // Try to get token from localStorage (simulated for demo)
    // In real app: const storedToken = localStorage.getItem("jwt_token");
    const storedToken = null; // Simulated for Claude artifacts

    if (storedToken && this.isTokenValid(storedToken)) {
      this.token = storedToken;
      return this.token;
    }

    // If no valid token, authenticate
    return await this.authenticate();
  }

  private async authenticate(): Promise<string> {
    // Prevent multiple simultaneous login attempts
    if (this.loginPromise) {
      return this.loginPromise;
    }

    this.loginPromise = this.performLogin();
    
    try {
      const token = await this.loginPromise;
      return token;
    } finally {
      this.loginPromise = null;
    }
  }

  private async performLogin(): Promise<string> {
    try {
      const response = await fetch("http://192.168.0.19:8090/login", {
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

      if (!response.ok) {
        throw new Error(`Login failed: ${response.status} ${response.statusText}`);
      }

      const data = await response.json();
      this.token = data.token;
      
      // In real app: localStorage.setItem("jwt_token", data.token);
      console.log("Login successful!");
      
      return this.token;
    } catch (error) {
      console.error("Authentication error:", error);
      throw new Error("Failed to authenticate. Please check your credentials.");
    }
  }

  private isTokenValid(token: string): boolean {
    if (!token) return false;
    
    try {
      // Decode JWT payload to check expiration
      const payload = JSON.parse(atob(token.split('.')[1]));
      const currentTime = Date.now() / 1000;
      
      // Check if token expires in the next 5 minutes (buffer time)
      return payload.exp > (currentTime + 300);
    } catch (error) {
      console.error("Token validation error:", error);
      return false;
    }
  }

  clearToken(): void {
    this.token = null;
    // In real app: localStorage.removeItem("jwt_token");
  }

  async makeAuthenticatedRequest(url: string, options: RequestInit = {}): Promise<Response> {
    const token = await this.getValidToken();
    
    const authenticatedOptions: RequestInit = {
      ...options,
      headers: {
        ...options.headers,
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      credentials: "include",
    };

    const response = await fetch(url, authenticatedOptions);
    
    // If unauthorized, clear token and retry once
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

// Create singleton instance
const authService = new AuthService();

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

  // Prevent multiple simultaneous submissions
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
    
    // Prevent multiple simultaneous submissions
    if (isSubmitting.current) {
      return;
    }

    isSubmitting.current = true;
    setIsLoading(true);
    setMessage(null);

    try {
      const response = await authService.makeAuthenticatedRequest(
        "http://192.168.0.19:8090/produto/adicionarProduto",
        {
          method: "POST",
          body: JSON.stringify(formData),
        }
      );

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Server error: ${response.status} - ${errorText || response.statusText}`);
      }

      await response.json(); // Parse response to ensure it's valid JSON

      setMessage({ 
        type: "success", 
        text: "Produto adicionado com sucesso!" 
      });

      // Reset form on success
      setFormData({
        id: 0,
        nome: "",
        categoria: "",
        price: 0,
        quantidade: 0,
        marca: "",
      });

    } catch (error) {
      console.error("Error adding product:", error);
      
      let errorMessage = "Erro ao adicionar produto";
      if (error instanceof Error) {
        if (error.message.includes("authenticate")) {
          errorMessage = "Erro de autenticação. Tente novamente.";
        } else if (error.message.includes("Server error")) {
          errorMessage = error.message;
        } else {
          errorMessage = error.message;
        }
      }

      setMessage({
        type: "error",
        text: errorMessage,
      });
    } finally {
      setIsLoading(false);
      isSubmitting.current = false;
    }
  };

  const handleClearForm = () => {
    setFormData({
      id: 0,
      nome: "",
      categoria: "",
      price: 0,
      quantidade: 0,
      marca: "",
    });
    setMessage(null);
  };

  const isFormValid =
    formData.nome.trim() &&
    formData.categoria.trim() &&
    formData.marca.trim() &&
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
                className={`mb-6 ${
                  message.type === "success" 
                    ? "border-green-200 bg-green-50" 
                    : "border-red-200 bg-red-50"
                }`}
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

            <div className="space-y-6">
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
                    disabled={isLoading}
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
                    disabled={isLoading}
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
                  disabled={isLoading}
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
                    disabled={isLoading}
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
                    disabled={isLoading}
                    className="w-full"
                  />
                </div>
              </div>

              <div className="flex gap-4 pt-4">
                <Button
                  onClick={handleSubmit}
                  disabled={!isFormValid || isLoading}
                  className="flex-1 bg-blue-600 hover:bg-blue-700 disabled:opacity-50"
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
                  onClick={handleClearForm}
                  disabled={isLoading}
                  className="disabled:opacity-50"
                >
                  Limpar
                </Button>
              </div>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}