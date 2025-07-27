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
import { Loader2, Package, CheckCircle, AlertCircle, XCircle, Search } from "lucide-react";
import { authService } from "@/components/jwt-token";

// Interface para os dados do produto
interface ProductData {
    id: number;
    nome: string;
    categoria: string;
    price: number;
    quantidade: number;
    marca: string;
}

// Componente principal da página de atualização
export default function UpdateProductPage() {
    // Estado para armazenar o ID digitado pelo usuário
    const [productId, setProductId] = useState<string>("");
    
    // Estado para controlar a visibilidade do modal
    const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
    
    // Estado para os dados do formulário dentro do modal
    const [formData, setFormData] = useState<ProductData>({
        id: 0,
        nome: "",
        categoria: "",
        price: 0,
        quantidade: 0,
        marca: "",
    });

    // Estados de carregamento
    const [isSearching, setIsSearching] = useState<boolean>(false);
    const [isUpdating, setIsUpdating] = useState<boolean>(false);

    // Estados para mensagens de feedback
    const [searchError, setSearchError] = useState<string | null>(null);
    const [updateMessage, setUpdateMessage] = useState<{
        type: "success" | "error";
        text: string;
    } | null>(null);

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


    // Função para buscar o produto pelo ID
    const handleSearchProduct = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!productId) {
            setSearchError("Por favor, insira um ID de produto.");
            return;
        }

        setIsSearching(true);
        setSearchError(null);
        setUpdateMessage(null);

        try {
            // URL ATUALIZADA para buscar um produto por ID
            const resp = await authService.makeAuthenticatedRequest(
                `http://192.168.0.19:8090/produto/public/produtos/${productId}`,
                { method: "GET" }
            );

            if (!resp.ok) {
                 if (resp.status === 404) {
                    throw new Error(`Produto com ID ${productId} não encontrado.`);
                }
                const errorText = await resp.text();
                throw new Error(`Erro no servidor: ${resp.status} - ${errorText || resp.statusText}`);
            }

            const productData: ProductData = await resp.json();
            setFormData(productData);
            setIsModalOpen(true); // Abre o modal com os dados do produto

        } catch (error) {
            console.error("Erro ao buscar o produto:", error);
            setSearchError(error instanceof Error ? error.message : "Ocorreu um erro desconhecido.");
        } finally {
            setIsSearching(false);
        }
    };

    // Manipulador para mudanças nos inputs do formulário do modal
    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: name === "price" || name === "quantidade" ? parseFloat(value) || 0 : value,
        }));
    };

    // Função para submeter a atualização do produto (dentro do modal)
    const handleUpdateSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (isSubmitting.current) return;

        isSubmitting.current = true;
        setIsUpdating(true);
        setUpdateMessage(null);

        try {
            // URL ATUALIZADA para fazer o update do produto
            // Nota: O endpoint parece específico para quantidade, mas o formulário envia todos os dados.
            // O backend deve estar preparado para lidar com o corpo da requisição completo.
            const resp = await authService.makeAuthenticatedRequest(
                `http://192.168.0.19:8090/produto/updateProduto/${formData.id}`,
                {
                    method: "PUT",
                    body: JSON.stringify(formData),
                }
            );

            if (!resp.ok) {
                const errorText = await resp.text();
                throw new Error(`Erro no servidor: ${resp.status} - ${errorText || resp.statusText}`);
            }

            // Exibe a mensagem de sucesso, fecha o modal e limpa o campo de ID
            setUpdateMessage({ type: "success", text: "Produto atualizado com sucesso!" });
            setIsModalOpen(false);
            setProductId("");

        } catch (error) {
            console.error("Erro ao atualizar o produto:", error);
            setUpdateMessage({
                type: "error",
                text: `Erro ao atualizar o produto: ${error instanceof Error ? error.message : "Erro desconhecido"}`,
            });
        } finally {
            setIsUpdating(false);
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
        <div className="min-h-screen bg-gray-50 py-12 px-4 font-sans">
            <div className="max-w-xl mx-auto">
                <Card>
                    <CardHeader>
                        <div className="flex items-center gap-3">
                            <Package className="h-6 w-6 text-blue-600" />
                            <CardTitle className="text-2xl">Atualizar Produto</CardTitle>
                        </div>
                        <CardDescription>
                            Insira o ID do produto que você deseja atualizar.
                        </CardDescription>
                    </CardHeader>
                    <CardContent>
                        <form onSubmit={handleSearchProduct} className="space-y-4">
                            <div className="space-y-2">
                                <Label htmlFor="productId">ID do Produto</Label>
                                <div className="flex gap-2">
                                    <Input
                                        id="productId"
                                        name="productId"
                                        type="number"
                                        placeholder="Digite o ID do produto"
                                        value={productId}
                                        onChange={(e) => setProductId(e.target.value)}
                                        className="flex-grow"
                                        required
                                    />
                                    <Button type="submit" disabled={isSearching} className="w-40">
                                        {isSearching ? (
                                            <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                                        ) : (
                                            <Search className="mr-2 h-4 w-4" />
                                        )}
                                        Buscar
                                    </Button>
                                </div>
                            </div>
                        </form>
                        
                        {/* Mensagens de erro da busca ou sucesso da atualização */}
                        {(searchError || updateMessage) && (
                            <Alert className={`mt-4 ${updateMessage?.type === 'success' ? 'border-green-500/50 bg-green-50 text-green-700' : 'border-red-500/50 bg-red-50 text-red-700'}`}>
                                {updateMessage?.type === 'success' ? <CheckCircle className="h-4 w-4" /> : <AlertCircle className="h-4 w-4" />}
                                <AlertDescription>
                                    {searchError || updateMessage?.text}
                                </AlertDescription>
                            </Alert>
                        )}

                    </CardContent>
                </Card>
            </div>

            {/* Modal para Atualização */}
            {isModalOpen && (
                <div 
                    className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-60 backdrop-blur-sm"
                    onClick={() => setIsModalOpen(false)} // Fecha o modal ao clicar no fundo
                >
                    <div 
                        className="relative w-full max-w-2xl mx-4"
                        onClick={(e) => e.stopPropagation()} // Impede que o clique no card feche o modal
                    >
                        <Card>
                            <form onSubmit={handleUpdateSubmit}>
                                <CardHeader>
                                    <CardTitle>Editando Produto ID: {formData.id}</CardTitle>
                                    <CardDescription>Modifique os dados abaixo e clique em salvar.</CardDescription>
                                </CardHeader>
                                <CardContent className="space-y-4">
                                     {/* Mensagem de erro/sucesso DENTRO do modal */}
                                    {updateMessage && updateMessage.type === 'error' && (
                                        <Alert className="border-red-500/50 bg-red-50 text-red-700">
                                            <AlertCircle className="h-4 w-4" />
                                            <AlertDescription>{updateMessage.text}</AlertDescription>
                                        </Alert>
                                    )}

                                    <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                        <div className="space-y-1">
                                            <Label htmlFor="nome">Nome do Produto *</Label>
                                            <Input id="nome" name="nome" value={formData.nome} onChange={handleInputChange} required />
                                        </div>
                                        <div className="space-y-1">
                                            <Label htmlFor="categoria">Categoria *</Label>
                                            <Input id="categoria" name="categoria" value={formData.categoria} onChange={handleInputChange} required />
                                        </div>
                                    </div>
                                    <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                                        <div className="space-y-1">
                                            <Label htmlFor="price">Preço *</Label>
                                            <Input id="price" name="price" type="number" step="0.01" value={formData.price} onChange={handleInputChange} required />
                                        </div>
                                        <div className="space-y-1">
                                            <Label htmlFor="quantidade">Quantidade *</Label>
                                            <Input id="quantidade" name="quantidade" type="number" value={formData.quantidade} onChange={handleInputChange} required />
                                        </div>
                                        <div className="space-y-1">
                                            <Label htmlFor="marca">Marca *</Label>
                                            <Input id="marca" name="marca" value={formData.marca} onChange={handleInputChange} required />
                                        </div>
                                    </div>
                                </CardContent>
                                <CardFooter className="flex justify-end gap-2">
                                    <Button type="button" variant="outline" onClick={() => setIsModalOpen(false)}>
                                        Cancelar
                                    </Button>
                                    <Button type="submit" disabled={!isFormValid || isUpdating}>
                                        {isUpdating && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
                                        Salvar Alterações
                                    </Button>
                                </CardFooter>
                                {/* Botão de fechar no canto */}
                                <Button 
                                  type="button"
                                  variant="ghost"
                                  size="icon"
                                  className="absolute top-3 right-3 rounded-full h-8 w-8"
                                  onClick={() => setIsModalOpen(false)}
                                >
                                  <XCircle className="h-5 w-5 text-gray-500" />
                                </Button>
                            </form>
                        </Card>
                    </div>
                </div>
            )}
        </div>
    );
}
