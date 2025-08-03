"use client";

import React, { useState } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Package, PlusCircle, Trash2, Search, Edit, ArrowLeft } from "lucide-react";

import AddClientPage from "@/components/client/add-client";
import DeleteClientPage from "@/components/client/remove-client";
import FindClientPage from "@/components/client/find-client";
import ModifyClientPage from "@/components/client/update-client";


type Page = "menu" | "add" | "delete" | "find" | "modify";


export default function MenuClientPage() {
  const [currentPage, setCurrentPage] = useState<Page>("menu");

  const renderContent = () => {
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
            <AddClientPage />
          </div>
        );
      case "delete":
        return (
          <div>
            <BackButton />
            <DeleteClientPage />
          </div>
        );
      case "find":
        return (
          <div>
            <BackButton />
            <FindClientPage />
          </div>
        );
      case "modify":
        return (
          <div>
            <BackButton />
            <ModifyClientPage />
          </div>
        );
      case "menu":
      default:
        return (
            <Card>
                <CardHeader>
                    <div className="flex items-center gap-3">
                        <Package className="h-8 w-8 text-blue-350" />
                        <CardTitle className="text-3xl font-semibold"> Menu de Clientes </CardTitle>
                    </div>

                    <CardDescription>
                        Selecione uma das opções abaixo para gerenciar os clientes.
                    </CardDescription>
                </CardHeader>
                <CardContent className="grid grid-cols-1 sm:grid-cols-2 gap4">
                    <Button onClick={() => setCurrentPage('add')} className="p-8 text-lg justify-start">
                        <PlusCircle className="mr-2 h-4 w-4" />
                        Adicionar Cliente
                    </Button>
                    <Button onClick={() => setCurrentPage('delete')} className="p-8 text-lg justify-start">
                        <Trash2 className="mr-2 h-4 w-4" />
                        Deletar Cliente
                    </Button>
                    <Button onClick={() => setCurrentPage('find')} className="p-8 text-lg justify-start">
                        <Search className="mr-2 h-4 w-4" />
                        Buscar Cliente
                    </Button>
                    <Button onClick={() => setCurrentPage('modify')} className="p-8 text-lg justify-start">
                        <Edit className="mr-2 h-4 w-4" />
                        Modificar Cliente
                    </Button>
                </CardContent>
            </Card>
        );
    }
};

    return (
        <div className="min-h-screen bg-gray-50 flex items-center justify-center p-4">
            <div className="w-full max-w-3xl">
                {renderContent()}
            </div>
        </div>
    );

}
