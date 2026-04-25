package service;

import exception.CuentaInactivaException;
import exception.LimiteExtraccionExcedidoException;
import exception.SaldoInsuficienteException;
import model.CuentaBancaria;
import model.TipoTransaccion;
import model.Transaccion;

import java.util.ArrayList;
import java.util.List;

public class CajeroService {
    
    // El límite establecido por el Trabajo Práctico
    private static final double LIMITE_EXTRACCION = 10000.00;
    
    // Lista para gestionar las cuentas del sistema (Requerido por tu Main)
    private List<CuentaBancaria> cuentasRegistradas;

    public CajeroService() {
        this.cuentasRegistradas = new ArrayList<>();
    }

    // ── MÉTODOS DE GESTIÓN DE CUENTAS ───────────────────────────────────────

    public void registrarCuenta(CuentaBancaria cuenta) {
        cuentasRegistradas.add(cuenta);
    }

    public void desactivarCuenta(CuentaBancaria cuenta) {
        cuenta.setActiva(false);
    }

    // ── MÉTODOS DE OPERACIONES BANCARIAS ────────────────────────────────────

    /**
     * Realiza un depósito: valida monto positivo, actualiza y registra.
     */
    public void depositar(CuentaBancaria cuenta, double monto) throws CuentaInactivaException {
        validarCuentaActiva(cuenta);
        
        if (monto <= 0) {
            // Lanza IllegalArgumentException (como espera tu Main en la TX 14)
            throw new IllegalArgumentException("El monto a depositar debe ser mayor a cero.");
        }

        // Actualizamos saldo
        cuenta.setSaldo(cuenta.getSaldo() + monto);
        
        // Registramos transacción
        Transaccion t = new Transaccion(TipoTransaccion.DEPOSITO, monto, "Depósito por ventanilla/cajero", cuenta.getSaldo());
        cuenta.agregarTransaccion(t);
    }

    /**
     * Realiza una extracción: valida saldo, límite diario, actualiza y registra.
     */
    public void extraer(CuentaBancaria cuenta, double monto) 
            throws CuentaInactivaException, LimiteExtraccionExcedidoException, SaldoInsuficienteException {
        validarCuentaActiva(cuenta);
        
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a extraer debe ser mayor a cero.");
        }
        if (monto > LIMITE_EXTRACCION) {
            throw new LimiteExtraccionExcedidoException("El monto $" + monto + " supera el límite por operación de $" + LIMITE_EXTRACCION);
        }
        if (cuenta.getSaldo() < monto) {
            throw new SaldoInsuficienteException("Saldo insuficiente. Saldo actual: $" + cuenta.getSaldo());
        }

        // Actualizamos saldo
        cuenta.setSaldo(cuenta.getSaldo() - monto);
        
        // Registramos transacción
        Transaccion t = new Transaccion(TipoTransaccion.EXTRACCION, monto, "Extracción en cajero", cuenta.getSaldo());
        cuenta.agregarTransaccion(t);
    }

    /**
     * Realiza una transferencia: operación atómica entre dos cuentas.
     */
    public void transferir(CuentaBancaria origen, CuentaBancaria destino, double monto) 
            throws CuentaInactivaException, SaldoInsuficienteException {
        
        // Validamos que AMBAS cuentas estén activas
        validarCuentaActiva(origen);
        validarCuentaActiva(destino);
        
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a transferir debe ser mayor a cero.");
        }
        if (origen.getSaldo() < monto) {
            throw new SaldoInsuficienteException("Saldo insuficiente en la cuenta de origen para transferir.");
        }

        // Operación atómica: restamos de origen y sumamos a destino
        origen.setSaldo(origen.getSaldo() - monto);
        destino.setSaldo(destino.getSaldo() + monto);

        // Registramos transacciones en los historiales de AMBAS cuentas
        Transaccion tOrigen = new Transaccion(TipoTransaccion.TRANSFERENCIA, monto, "Transferencia enviada a " + destino.getTitular(), origen.getSaldo());
        Transaccion tDestino = new Transaccion(TipoTransaccion.TRANSFERENCIA, monto, "Transferencia recibida de " + origen.getTitular(), destino.getSaldo());
        
        origen.agregarTransaccion(tOrigen);
        destino.agregarTransaccion(tDestino);
    }

    /**
     * Consulta el saldo: devuelve el saldo sin modificar el estado.
     */
    public double consultarSaldo(CuentaBancaria cuenta) throws CuentaInactivaException {
        validarCuentaActiva(cuenta);
        
        // OPCIONAL: Registrar en el historial que se hizo una consulta.
        // Lo dejamos comentado porque el TP dice explícitamente "sin modificar estado". 
        // Si no agregamos la transacción, cumplimos esa regla a rajatabla.
        
        /* Transaccion t = new Transaccion(TipoTransaccion.CONSULTA, 0.0, "Consulta de saldo", cuenta.getSaldo());
        cuenta.agregarTransaccion(t);
        */

        return cuenta.getSaldo();
    }

    // ── MÉTODOS PRIVADOS DE AYUDA ───────────────────────────────────────────

    /**
     * Método centralizado para validar si una cuenta está inactiva.
     */
    private void validarCuentaActiva(CuentaBancaria cuenta) throws CuentaInactivaException {
        if (!cuenta.isActiva()) {
            throw new CuentaInactivaException("Operación rechazada: La cuenta " + cuenta.getNumeroCuenta() + " se encuentra inactiva.");
        }
    }
}