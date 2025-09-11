"use client";

import React, { useState } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Package, PlusCircle, Trash2, Search, Edit, ArrowLeft } from "lucide-react";

// Import your page components
import AddProductPage from "@/components/employee/add-employee.tsx";
import DeleteProductPage from "@/components/employee/delete-employee";
import FindProductPage from "@/components/employee/find-employee";
import ModifyProductPage from "@/components/employee/update-employee";

type Page = "menu" | "add" | "delete" | "find" | "modify";

export default function MenuEmployeePage() {
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
                        <div className="flex items-center justify-between gap-4">
                            <Package className="h-8 w-8 text-blue-300" />
                            <CardTitle className="text-2xl font-semibold">Menu de Funcionários</CardTitle>
                        </div>

                        <CardDescription>
                            Selecione uma das opções abaixo para gerenciar os funcionários:
                        </CardDescription>
                    </CardHeader>

                    <CardContent className="grid grid-cols-1 sm:grid-cols-2 gap-4 mt-4">
                            <Button onClick={() => setCurrentPage('add')} className="p-8 text-lg justify-start">
                                <PlusCircle className="mr-2 h-5 w-5" />
                                Adicionar Funcionário
                            </Button>

                            <Button onClick={() => setCurrentPage('delete')} className="p-8 text-lg justify-start" variant="destructive">
                                <Trash2 className="mr-2 h-5 w-5" />
                                Deletar Funcionário
                            </Button>

                            <Button onClick={() => setCurrentPage('find')} className="p-8 text-lg justify-start">
                                <Search className="mr-2 h-5 w-5" />
                                Buscar Funcionário
                            </Button>   

                            <Button onClick={() => setCurrentPage('modify')} className="p-8 text-lg justify-start">
                                <Edit className="mr-2 h-5 w-5" />
                                Modificar Funcionário
                            </Button>

                    </CardContent>
                </Card>
            )

        }

    };

    return (
        <div className= "min-h-screen bg-green-120 flex items-center justify-center p-4">
            <div className="w-full max-w-4xl">
                {renderContent()}
            </div>
        </div>
    );

}