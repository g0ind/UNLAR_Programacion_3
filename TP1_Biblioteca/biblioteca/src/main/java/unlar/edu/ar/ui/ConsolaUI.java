package unlar.edu.ar.ui;

import unlar.edu.ar.service.BibliotecaService;

public class ConsolaUI {
    private BibliotecaService service;

    // Este constructor permite que la UI conozca al Service
    public ConsolaUI(BibliotecaService service) {
        this.service = service;
    }

    public void mostrarBienvenida() {
        System.out.println("========================================");
        System.out.println("   SISTEMA DE GESTIÓN BIBLIOTECARIA    ");
        System.out.println("          UNLaR - Prog III             ");
        System.out.println("========================================\n");
    }
}