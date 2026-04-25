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

        // Inicialización de 3 cuentas requeridas
        CuentaBancaria cuenta1 = new CuentaBancaria("CUENTA-001", "Ana Garcia", new BigDecimal("50000.00"));
        CuentaBancaria cuenta2 = new CuentaBancaria("CUENTA-002", "Luis Perez", new BigDecimal("15000.00"));
        CuentaBancaria cuenta3 = new CuentaBancaria("CUENTA-003", "Maria Lopez", new BigDecimal("2000.00"));

        System.out.println("\n--- EJECUTANDO 15 TRANSACCIONES AUTOMÁTICAS ---");

        try {
            // ==========================================
            // □ CONSULTA DE SALDO (Sin modificar estado)
            // ==========================================
            System.out.println("\n>> CONSULTAS DE SALDO:");
            System.out.println("T1 - Saldo C1: " + FormatoUtil.formatearMoneda(cuenta1.getSaldo().doubleValue()));
            System.out.println("T2 - Saldo C2: " + FormatoUtil.formatearMoneda(cuenta2.getSaldo().doubleValue()));
            System.out.println("T3 - Saldo C3: " + FormatoUtil.formatearMoneda(cuenta3.getSaldo().doubleValue()));

            // ==========================================
            // □ DEPÓSITO (Validar monto positivo, actualizar, registrar)
            // ==========================================
            System.out.println("\n>> DEPÓSITOS:");
            cajeroService.depositar(cuenta1, new BigDecimal("5000.00")); // T4
            System.out.println("T4 - Depósito exitoso en C1. Nuevo saldo: " + FormatoUtil.formatearMoneda(cuenta1.getSaldo().doubleValue()));
            
            cajeroService.depositar(cuenta2, new BigDecimal("3000.00")); // T5
            System.out.println("T5 - Depósito exitoso en C2. Nuevo saldo: " + FormatoUtil.formatearMoneda(cuenta2.getSaldo().doubleValue()));
            
            cajeroService.depositar(cuenta3, new BigDecimal("1500.00")); // T6
            System.out.println("T6 - Depósito exitoso en C3. Nuevo saldo: " + FormatoUtil.formatearMoneda(cuenta3.getSaldo().doubleValue()));

            // ==========================================
            // □ EXTRACCIÓN (Validar saldo, límite diario, actualizar)
            // ==========================================
            System.out.println("\n>> EXTRACCIONES:");
            cajeroService.extraer(cuenta1, new BigDecimal("8000.00")); // T7 (Válida)
            System.out.println("T7 - Extracción exitosa C1.");
            
            cajeroService.extraer(cuenta2, new BigDecimal("1000.00")); // T8 (Válida)
            System.out.println("T8 - Extracción exitosa C2.");
            
            cajeroService.extraer(cuenta3, new BigDecimal("500.00"));  // T9 (Válida)
            System.out.println("T9 - Extracción exitosa C3.");

            // ==========================================
            // □ TRANSFERENCIA (Transacción atómica entre cuentas)
            // ==========================================
            System.out.println("\n>> TRANSFERENCIAS:");
            cajeroService.transferir(cuenta1, cuenta2, new BigDecimal("2000.00")); // T10
            System.out.println("T10 - Transferencia de C1 a C2 por $2,000.00 exitosa.");
            
            cajeroService.transferir(cuenta2, cuenta3, new BigDecimal("1000.00")); // T11
            System.out.println("T11 - Transferencia de C2 a C3 por $1,000.00 exitosa.");
            
            cajeroService.transferir(cuenta3, cuenta1, new BigDecimal("500.00"));  // T12
            System.out.println("T12 - Transferencia de C3 a C1 por $500.00 exitosa.");

        } catch (Exception e) {
            System.out.println("Error inesperado en transacciones válidas: " + e.getMessage());
        }

        // ==========================================
        // EXCEPCIONES FORZADAS (Manejo de errores del TP)
        // ==========================================
        System.out.println("\n>> FORZANDO EXCEPCIONES:");

        // T13 - Forzando Límite Excedido (Límite es $10,000 según TP)
        try {
            System.out.println("T13 - Intentando extraer $15,000 de C1...");
            cajeroService.extraer(cuenta1, new BigDecimal("15000.00"));
        } catch (LimiteExtraccionExcedidoException | SaldoInsuficienteException | CuentaInactivaException e) {
            System.out.println("--> EXCEPCIÓN ATRAPADA: " + e.getMessage());
        }

        // T14 - Forzando Saldo Insuficiente
        try {
            System.out.println("T14 - Intentando transferir $100,000 de C2 a C3...");
            cajeroService.transferir(cuenta2, cuenta3, new BigDecimal("100000.00"));
        } catch (LimiteExtraccionExcedidoException | SaldoInsuficienteException | CuentaInactivaException e) {
            System.out.println("--> EXCEPCIÓN ATRAPADA: " + e.getMessage());
        }

        // T15 - Forzando Cuenta Inactiva
        try {
            System.out.println("T15 - Desactivando C3 e intentando extraer...");
            cuenta3.setActiva(false); // Inactivación administrativa
            cajeroService.extraer(cuenta3, new BigDecimal("100.00"));
        } catch (LimiteExtraccionExcedidoException | SaldoInsuficienteException | CuentaInactivaException e) {
            System.out.println("--> EXCEPCIÓN ATRAPADA: " + e.getMessage());
        }

        System.out.println("\n==========================================");
        System.out.println("SIMULACIÓN FINALIZADA - ABRIENDO UI");
        System.out.println("==========================================\n");

        // Abrimos la interfaz gráfica de consola usando la cuenta 1 para probar manualmente
        MenuCajeroUI ui = new MenuCajeroUI(cajeroService);
        ui.iniciar(cuenta1);
    }
}