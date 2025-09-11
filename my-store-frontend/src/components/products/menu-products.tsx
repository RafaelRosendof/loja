"use client";

import React, { useState } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Package, PlusCircle, Trash2, Search, Edit, ArrowLeft } from "lucide-react";

// Import your page components
import AddProductPage from "@/components/products/add-product-page";
import DeleteProductPage from "@/components/products/delete-product";
import FindProductPage from "@/components/products/find-product";
import ModifyProductPage from "@/components/products/update-product-page";


type Page = "menu" | "add" | "delete" | "find" | "modify";

export default function MenuProductsPage() {
  const [currentPage, setCurrentPage] = useState<Page>("menu");

  const renderContent = () => {
    // Button to go back to the menu, which we can pass to child components
    const BackButton = () => (
         <Button variant="outline" onClick={() => setCurrentPage('menu')} className="mb-8">
            <ArrowLeft className="mr-2 h-4 w-4" />
            Voltar ao Menu
        </Button>
    );

    switch (currentPage) {
      case "add":
        return (
            <div>
                <BackButton />
                <AddProductPage />
            </div>
        );
      case "delete":
        return (
            <div>
                <BackButton />
                <DeleteProductPage />
            </div>
        );
      case "find":
        return (
            <div>
                <BackButton />
                <FindProductPage />
            </div>
        );
       case "modify":
        return (
            <div>
                <BackButton />
                <ModifyProductPage />
            </div>
        );
      case "menu":
      default:
        return (
          <Card>
            <CardHeader>
              <div className="flex items-center gap-3">
                <Package className="h-8 w-8 text-blue-600" />
                <CardTitle className="text-3xl">Menu de Produtos</CardTitle>
              </div>
              <CardDescription>
                Selecione uma das opções abaixo para gerenciar os produtos.
              </CardDescription>
            </CardHeader>
            <CardContent className="grid grid-cols-1 sm:grid-cols-2 gap-4">
               <Button onClick={() => setCurrentPage('add')} className="p-8 text-lg justify-start">
                  <PlusCircle className="mr-4 h-6 w-6" />
                  Adicionar Produto
              </Button>
               <Button onClick={() => setCurrentPage('delete')} className="p-8 text-lg justify-start" variant="destructive">
                  <Trash2 className="mr-4 h-6 w-6" />
                  Deletar Produto
              </Button>
               <Button onClick={() => setCurrentPage('find')} className="p-8 text-lg justify-start">
                  <Search className="mr-4 h-6 w-6" />
                  Buscar Produto
              </Button>
               <Button onClick={() => setCurrentPage('modify')} className="p-8 text-lg justify-start">
                  <Edit className="mr-4 h-6 w-6" />
                  Modificar Produto
              </Button>
            </CardContent>
          </Card>
        );
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 flex items-center justify-center p-4">
      <div className="w-full max-w-4xl">
        {renderContent()}
      </div>
    </div>
  );
}