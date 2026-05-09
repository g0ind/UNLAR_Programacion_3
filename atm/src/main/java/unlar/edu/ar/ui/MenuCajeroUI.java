package unlar.edu.ar.ui;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import unlar.edu.ar.exception.CuentaInactivaException;
import unlar.edu.ar.exception.LimiteExtraccionExcedidoException;
import unlar.edu.ar.exception.SaldoInsuficienteException;
import unlar.edu.ar.model.CuentaBancaria;
import unlar.edu.ar.model.Transaccion;
import unlar.edu.ar.service.CajeroService;
import unlar.edu.ar.util.FormatoUtil;

public class MenuCajeroUI {
    private final CajeroService cajeroService;
    private final Scanner scanner;

    public MenuCajeroUI(CajeroService cajeroService) {
        this.cajeroService = cajeroService;
        this.scanner = new Scanner(System.in);
    }

    // Bucle principal con opción de salida
    public void iniciar(CuentaBancaria cuentaActiva) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n========================================");
            System.out.println("CAJERO AUTOMÁTICO - UNLaR");
            System.out.println("Cuenta: " + cuentaActiva.getNumeroCuenta() + " | Titular: " + cuentaActiva.getTitular());
            System.out.println("========================================");
            System.out.println("1. Consultar Saldo");
            System.out.println("2. Depositar");
            System.out.println("3. Extraer");
            // Agregamos la opción de transferencia requerida por el TP
            System.out.println("4. Transferir"); 
            System.out.println("5. Ver Historial (Últimos 10 movimientos)");
            System.out.println("6. Salir");
            System.out.print("Ingrese una opción: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer

                // Cumpliendo el requisito: Menú interactivo usando switch expression 
                String resultado = switch (opcion) {
                    case 1 -> consultarSaldo(cuentaActiva);
                    case 2 -> realizarDeposito(cuentaActiva);
                    case 3 -> realizarExtraccion(cuentaActiva);
                    case 4 -> realizarTransferencia(cuentaActiva);
                    case 5 -> verHistorial(cuentaActiva);
                    case 6 -> {
                        salir = true;
                        yield "Saliendo del sistema... ¡Gracias por operar con nosotros!";
                    }
                    default -> "Opción no válida. Por favor, seleccione un número del 1 al 6.";
                };

                System.out.println("\n--> " + resultado);

            // Cumpliendo el requisito: Validación de entrada numérica 
            } catch (InputMismatchException e) {
                System.out.println("\n--> ERROR: Entrada inválida. Debe ingresar un número entero.");
                scanner.nextLine(); // Limpiar el buffer corrupto para evitar bucle infinito
            } catch (Exception e) {
                System.out.println("\n--> ERROR INESPERADO: " + e.getMessage());
            }
        }
    }

    private String consultarSaldo(CuentaBancaria cuenta) {
        try {
            double saldo = cajeroService.consultarSaldo(cuenta);
            return "Su saldo actual es: " + FormatoUtil.formatearMoneda(saldo);
        } catch (CuentaInactivaException e) {
            return "ERROR: " + e.getMessage();
        }
    }

    private String realizarDeposito(CuentaBancaria cuenta) {
        System.out.print("Ingrese el monto a depositar: $");
        try {
            double monto = scanner.nextDouble();
            scanner.nextLine();
            
            cajeroService.depositar(cuenta, monto);
            return "Depósito de " + FormatoUtil.formatearMoneda(monto) + " realizado con éxito.";
            
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return "Operación cancelada: El monto ingresado no es válido.";
        } catch (IllegalArgumentException | CuentaInactivaException e) {
            return "OPERACIÓN RECHAZADA: " + e.getMessage();
        }
    }

    private String realizarExtraccion(CuentaBancaria cuenta) {
        System.out.print("Ingrese el monto a extraer: $");
        try {
            double monto = scanner.nextDouble();
            scanner.nextLine();

            cajeroService.extraer(cuenta, monto);
            return "Extracción exitosa. Por favor, retire sus " + FormatoUtil.formatearMoneda(monto) + ".";

        } catch (InputMismatchException e) {
            scanner.nextLine();
            return "Operación cancelada: El monto ingresado no es válido.";
        } catch (SaldoInsuficienteException | LimiteExtraccionExcedidoException | CuentaInactivaException e) {
            return "OPERACIÓN RECHAZADA: " + e.getMessage();
        }
    }

    private String realizarTransferencia(CuentaBancaria cuentaOrigen) {
        System.out.print("Ingrese el número de cuenta destino: ");
        String cuentaDestinoStr = scanner.nextLine();
        
        System.out.print("Ingrese el monto a transferir: $");
        try {
            double monto = scanner.nextDouble();
            scanner.nextLine();

            // Creamos cuenta destino activa para la simulación
            CuentaBancaria cuentaDestino = new CuentaBancaria(cuentaDestinoStr, "Destinatario Simulado", BigDecimal.ZERO);
            cuentaDestino.setActiva(true); 
            
            cajeroService.transferir(cuentaOrigen, cuentaDestino, monto);
            return "Transferencia de " + FormatoUtil.formatearMoneda(monto) + " enviada a la cuenta " + cuentaDestinoStr + ".";

        } catch (InputMismatchException e) {
            scanner.nextLine();
            return "Operación cancelada: El monto ingresado no es válido.";
        } catch (SaldoInsuficienteException | CuentaInactivaException e) {
            return "OPERACIÓN RECHAZADA: " + e.getMessage();
        }
    }

    private String verHistorial(CuentaBancaria cuenta) {
        List<Transaccion> historial = cuenta.getHistorialTransacciones();
        if (historial.isEmpty()) {
            return "No hay transacciones registradas en esta cuenta.";
        }

        System.out.println("\n--- ÚLTIMOS MOVIMIENTOS ---");
        int inicio = Math.max(0, historial.size() - 10);
        for (int i = inicio; i < historial.size(); i++) {
            System.out.println(historial.get(i).obtenerLog());
        }
        
        return "Fin del historial.";
    }
}