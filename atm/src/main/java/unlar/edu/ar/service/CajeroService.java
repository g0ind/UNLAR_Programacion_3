package unlar.edu.ar.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import unlar.edu.ar.exception.CuentaInactivaException;
import unlar.edu.ar.exception.LimiteExtraccionExcedidoException;
import unlar.edu.ar.exception.SaldoInsuficienteException;
import unlar.edu.ar.model.CuentaBancaria;
import unlar.edu.ar.model.TipoTransaccion;
import unlar.edu.ar.model.Transaccion;

public class CajeroService {
    
    // El límite establecido por el TP (10.000)
    private static final BigDecimal LIMITE_EXTRACCION = new BigDecimal("10000.00");
    
    private List<CuentaBancaria> cuentasRegistradas;

    public CajeroService() {
        this.cuentasRegistradas = new ArrayList<>();
    }

    // ── GESTIÓN DE CUENTAS ──────────────────────────────────────────────────

    public void registrarCuenta(CuentaBancaria cuenta) {
        cuentasRegistradas.add(cuenta);
    }

    public void desactivarCuenta(CuentaBancaria cuenta) {
        cuenta.setActiva(false);
    }

    // ── OPERACIONES BANCARIAS ───────────────────────────────────────────────

    /**
     * DEPÓSITO: Valida cuenta activa y monto positivo.
     */
    public void depositar(CuentaBancaria cuenta, double monto) throws CuentaInactivaException {
        validarCuentaActiva(cuenta);
        
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a depositar debe ser mayor a cero.");
        }

        // Convertimos el double a BigDecimal para operar sin errores de precisión
        BigDecimal montoBD = BigDecimal.valueOf(monto);
        cuenta.setSaldo(cuenta.getSaldo().add(montoBD));
        
        registrarTransaccion(cuenta, TipoTransaccion.DEPOSITO, monto, "Depósito realizado");
    }

    /**
     * EXTRACCIÓN: Valida saldo, límite de 10k y estado de cuenta.
     */
    public void extraer(CuentaBancaria cuenta, double monto) 
            throws CuentaInactivaException, LimiteExtraccionExcedidoException, SaldoInsuficienteException {
        
        validarCuentaActiva(cuenta);
        BigDecimal montoBD = BigDecimal.valueOf(monto);

        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a extraer debe ser mayor a cero.");
        }
        if (montoBD.compareTo(LIMITE_EXTRACCION) > 0) {
            throw new LimiteExtraccionExcedidoException("El monto supera el límite de $10,000 por operación.");
        }
        if (cuenta.getSaldo().compareTo(montoBD) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para extraer $" + monto);
        }

        // Restamos saldo
        cuenta.setSaldo(cuenta.getSaldo().subtract(montoBD));
        
        registrarTransaccion(cuenta, TipoTransaccion.EXTRACCION, monto, "Extracción en cajero");
    }

    /**
     * TRANSFERENCIA: Operación ATÓMICA entre dos cuentas.
     */
    public void transferir(CuentaBancaria origen, CuentaBancaria destino, double monto) 
            throws CuentaInactivaException, SaldoInsuficienteException {
        
        validarCuentaActiva(origen);
        validarCuentaActiva(destino);
        
        BigDecimal montoBD = BigDecimal.valueOf(monto);

        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a transferir debe ser mayor a cero.");
        }
        if (origen.getSaldo().compareTo(montoBD) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente en cuenta de origen.");
        }

        // Ejecución atómica
        origen.setSaldo(origen.getSaldo().subtract(montoBD));
        destino.setSaldo(destino.getSaldo().add(montoBD));

        // Registro en ambas cuentas
        registrarTransaccion(origen, TipoTransaccion.TRANSFERENCIA, monto, "Transferencia enviada a " + destino.getTitular());
        registrarTransaccion(destino, TipoTransaccion.TRANSFERENCIA, monto, "Transferencia recibida de " + origen.getTitular());
    }

    /**
     * CONSULTA DE SALDO: Devuelve el saldo actual como double para el Main.
     */
    public double consultarSaldo(CuentaBancaria cuenta) throws CuentaInactivaException {
        validarCuentaActiva(cuenta);
        // Según consigna, no modifica estado, así que solo retornamos el valor
        return cuenta.getSaldo().doubleValue();
    }

    // ── MÉTODOS DE APOYO (PRIVADOS) ─────────────────────────────────────────

    private void validarCuentaActiva(CuentaBancaria cuenta) throws CuentaInactivaException {
        if (!cuenta.isActiva()) {
            throw new CuentaInactivaException("Operación rechazada: Cuenta " + cuenta.getNumeroCuenta() + " inactiva.");
        }
    }

    private void registrarTransaccion(CuentaBancaria cuenta, TipoTransaccion tipo, double monto, String desc) {
        // Creamos el objeto transacción y lo agregamos a la lista de la cuenta
        Transaccion t = new Transaccion(tipo, monto, desc, cuenta.getSaldo().doubleValue());
        cuenta.agregarTransaccion(t);
    }
}