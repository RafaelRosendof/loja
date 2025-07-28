//import AddProductPage from "../components/add-product-page";

"use client";
import React, { useState } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Package, PlusCircle, Trash2, Search, Edit, ArrowLeft } from "lucide-react";

import MenuEmployeePage from "@/components/employee/menu-employee";

import MenuProductsPage from "@/components/products/menu-products";


export default function Page() {

  
    const [currentPage , setCurrentPage] = useState("menu");

    const renderContent = () => {

      const BackButton = () => (
        <Button variant="outline" onClick={() => setCurrentPage('menu')} className="mb-8">
          <ArrowLeft className="mr-2 h-4 w-4" />
          Voltar ao Menu
        </Button>
      );

      switch (currentPage) {
        case "Products":
          return (
            <div>
              <BackButton />
              <MenuProductsPage />
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
          return (
            <div className="grid grid-cols-1 gap-4">
              <Card onClick={() => setCurrentPage("Products")} className="cursor-pointer">
                <CardHeader>
                  <CardTitle>Produtos</CardTitle>
                  <CardDescription>Gerenciar produtos da loja</CardDescription>
                </CardHeader>
                <CardContent>
                  <Package className="h-6 w-6" />
                </CardContent>
              </Card>

              <Card onClick={() => setCurrentPage("Employees")} className="cursor-pointer">
                <CardHeader>
                  <CardTitle>Funcionários</CardTitle>
                  <CardDescription>Gerenciar funcionários da loja</CardDescription>
                </CardHeader>
                <CardContent>
                  <Package className="h-6 w-6" />
                </CardContent>
              </Card>

              <Card onClick={() => setCurrentPage("Clients")} className="cursor-pointer">
                <CardHeader>
                  <CardTitle>Clientes</CardTitle>
                  <CardDescription>Gerenciar clientes da loja</CardDescription>
                </CardHeader>
                <CardContent>
                  <Package className="h-6 w-6" />
                </CardContent>
              </Card>
            </div>
          )
      }
    };


  return (
    <div className="container mx-auto p-4">
      <h1 className="text-3xl font-bold mb-6">Bem-vindo ao Sistema de Gerenciamento da Loja</h1>
      
      {renderContent()}
    </div>

  );
  
}


