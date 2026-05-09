package unlar.edu.ar;

import unlar.edu.ar.exception.CuentaInactivaException;
import unlar.edu.ar.exception.LimiteExtraccionExcedidoException;
import unlar.edu.ar.exception.SaldoInsuficienteException;
import unlar.edu.ar.model.CuentaBancaria;
import unlar.edu.ar.service.CajeroService;
import unlar.edu.ar.ui.MenuCajeroUI;
import unlar.edu.ar.util.FormatoUtil;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("INICIANDO SIMULACIÓN - CAJERO AUTOMÁTICO");
        System.out.println("==========================================");
        
        CajeroService cajeroService = new CajeroService();

        // Los objetos CuentaBancaria sí usan BigDecimal en su constructor según tu modelo
        CuentaBancaria cuenta1 = new CuentaBancaria("CUENTA-001", "Ana Garcia", new BigDecimal("50000.00"));
        CuentaBancaria cuenta2 = new CuentaBancaria("CUENTA-002", "Luis Perez", new BigDecimal("15000.00"));
        CuentaBancaria cuenta3 = new CuentaBancaria("CUENTA-003", "Maria Lopez", new BigDecimal("2000.00"));

        System.out.println("\n--- EJECUTANDO 15 TRANSACCIONES AUTOMÁTICAS ---");

        try {
            // CONSULTAS DE SALDO
            System.out.println("\n>> CONSULTAS DE SALDO:");
            System.out.println("T1 - Saldo C1: " + FormatoUtil.formatearMoneda(cuenta1.getSaldo().doubleValue()));
            System.out.println("T2 - Saldo C2: " + FormatoUtil.formatearMoneda(cuenta2.getSaldo().doubleValue()));
            System.out.println("T3 - Saldo C3: " + FormatoUtil.formatearMoneda(cuenta3.getSaldo().doubleValue()));

            // DEPÓSITOS (Corregido: pasamos double directo)
            System.out.println("\n>> DEPÓSITOS:");
            cajeroService.depositar(cuenta1, 5000.00); // T4
            cajeroService.depositar(cuenta2, 3000.00); // T5
            cajeroService.depositar(cuenta3, 1500.00); // T6
            
            System.out.println("T4, T5 y T6 completadas.");

            // EXTRACCIONES (Corregido: pasamos double directo)
            System.out.println("\n>> EXTRACCIONES:");
            cajeroService.extraer(cuenta1, 8000.00); // T7
            cajeroService.extraer(cuenta2, 1000.00); // T8
            cajeroService.extraer(cuenta3, 500.00);  // T9
            System.out.println("T7, T8 y T9 completadas.");

            // TRANSFERENCIAS (Corregido: pasamos double directo)
            System.out.println("\n>> TRANSFERENCIAS:");
            cajeroService.transferir(cuenta1, cuenta2, 2000.00); // T10
            cajeroService.transferir(cuenta2, cuenta3, 1000.00); // T11
            cajeroService.transferir(cuenta3, cuenta1, 500.00);  // T12
            System.out.println("T10, T11 y T12 completadas.");

        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }

        // EXCEPCIONES FORZADAS
        System.out.println("\n>> FORZANDO EXCEPCIONES:");

        // T13 - Límite Excedido
        try {
            System.out.println("T13 - Intentando extraer $15,000 de C1...");
            cajeroService.extraer(cuenta1, 15000.00);
        } catch (LimiteExtraccionExcedidoException | SaldoInsuficienteException | CuentaInactivaException e) {
            System.out.println("--> EXCEPCIÓN ATRAPADA: " + e.getMessage());
        }

        // T14 - Saldo Insuficiente
        try {
            System.out.println("T14 - Intentando transferir $100,000 de C2 a C3...");
            cajeroService.transferir(cuenta2, cuenta3, 100000.00);
        } catch (SaldoInsuficienteException | CuentaInactivaException e) {
            System.out.println("--> EXCEPCIÓN ATRAPADA: " + e.getMessage());
        }

        // T15 - Cuenta Inactiva
        try {
            System.out.println("T15 - Desactivando C3 e intentando extraer...");
            cuenta3.setActiva(false);
            cajeroService.extraer(cuenta3, 100.00);
        } catch (LimiteExtraccionExcedidoException | SaldoInsuficienteException | CuentaInactivaException e) {
            System.out.println("--> EXCEPCIÓN ATRAPADA: " + e.getMessage());
        }

        System.out.println("\n==========================================");
        System.out.println("SIMULACIÓN FINALIZADA - ABRIENDO UI");
        System.out.println("==========================================\n");

        MenuCajeroUI ui = new MenuCajeroUI(cajeroService);
        ui.iniciar(cuenta1);
    }
}