//import AddProductPage from "../gerenciamento/add-product-page";

"use client";
import React, { useState } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Package, Users, UserCircle, ArrowLeft } from "lucide-react";

import MenuProductsPage from "@/components/products/menu-products";
import MenuEmployeePage from "@/components/employee/menu-employee";
import MenuClientPage from "@/components/client/menu-client";

export default function Page() {
    const [currentPage, setCurrentPage] = useState("menu");

    // Botão de Voltar reutilizável
    const BackButton = () => (
        <Button variant="ghost" onClick={() => setCurrentPage('menu')} className="mb-6 text-gray-600 hover:text-gray-900">
            <ArrowLeft className="mr-2 h-4 w-4" />
            Voltar ao Menu Principal
        </Button>
    );

    // Componente para o Menu Principal
    const MainMenu = () => (
        <div className="text-center">
            <h1 className="text-4xl font-bold text-gray-800">Seja bem vindo a Loja</h1>
            <p className="text-lg text-gray-500 mt-2 mb-10">Selecione o botão para comprar ou fazer login como funcionário.</p>
            
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
                {/* Card de Produtos */}
                <Card 
                    onClick={() => setCurrentPage("Products")} 
                    className="cursor-pointer group hover:shadow-xl hover:-translate-y-2 transition-all duration-300 ease-in-out border-2 border-transparent hover:border-blue-500"
                >
                    <CardHeader className="items-center text-center">
                        <div className="p-4 bg-blue-100 rounded-full mb-4 group-hover:bg-blue-500 transition-colors duration-300">
                            <Package className="h-10 w-10 text-blue-600 group-hover:text-white transition-colors duration-300" />
                        </div>
                        <CardTitle className="text-xl font-semibold text-gray-800">Loja</CardTitle>
                        <CardDescription className="text-gray-500">Comprar e conferir o catálogo de produtos</CardDescription>
                    </CardHeader>
                </Card>

                {/* Card de Funcionários */}
                <Card 
                    onClick={() => setCurrentPage("Employees")} 
                    className="cursor-pointer group hover:shadow-xl hover:-translate-y-2 transition-all duration-300 ease-in-out border-2 border-transparent hover:border-green-500"
                >
                    <CardHeader className="items-center text-center">
                        <div className="p-4 bg-green-100 rounded-full mb-4 group-hover:bg-green-500 transition-colors duration-300">
                            <Users className="h-10 w-10 text-green-600 group-hover:text-white transition-colors duration-300" />
                        </div>
                        <CardTitle className="text-xl font-semibold text-gray-800">Funcionários</CardTitle>
                        <CardDescription className="text-gray-500">Gerenciamento da loja</CardDescription>
                    </CardHeader>
                </Card>

                {/* Card de Clientes */}
                <Card 
                    onClick={() => setCurrentPage("Clients")} 
                    className="cursor-pointer group hover:shadow-xl hover:-translate-y-2 transition-all duration-300 ease-in-out border-2 border-transparent hover:border-green-500"
                >
                    <CardHeader className="items-center text-center">
                        <div className="p-4 bg-green-100 rounded-full mb-4 group-hover:bg-green-500 transition-colors duration-300">
                            <Users className="h-10 w-10 text-green-600 group-hover:text-white transition-colors duration-300" />
                        </div>
                        <CardTitle className="text-xl font-semibold text-gray-800">Clientes</CardTitle>
                        <CardDescription className="text-gray-500">Gerenciamento de clientes</CardDescription>
                    </CardHeader>
                </Card>
            </div>
        </div>
    );

    // Função para renderizar a página atual
    const renderContent = () => {
        switch (currentPage) {
            case "Products":
                return (
                    <div>
                        <BackButton />
                        <MenuProductsPage/>
                    </div>
                );
            case "Employees":
                return (
                    <div>
                        <BackButton />
                        <MenuEmployeePage />
                    </div>
                );
            case "Clients":
                return (
                    <div>
                        <BackButton />
                        <MenuClientPage />
                    </div>
                );
            case "menu":
            default:
                return <MainMenu />;
        }
    };

    return (
        // Container principal com um fundo suave e preenchimento
        <div className="bg-gray-200 min-h-screen flex items-center justify-center p-4 sm:p-6 lg:p-8">
            <div className="w-full max-w-6xl mx-auto">
                {renderContent()}
            </div>
        </div>
    );
}