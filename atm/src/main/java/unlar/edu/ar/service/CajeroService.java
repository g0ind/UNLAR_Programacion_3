package unlar.edu.ar.service;


import unlar.edu.ar.exception.LimiteExtraccionExcedidoException;
import unlar.edu.ar.exception.SaldoInsuficienteException;
import unlar.edu.ar.model.CuentaBancaria;
import unlar.edu.ar.model.Transaccion;
import unlar.edu.ar.model.TipoTransaccion;

import java.math.BigDecimal;

public class CajeroService {
    private static final BigDecimal LIMITE_EXTRACCION = new BigDecimal("10000.00");

    public void extraer(CuentaBancaria cuenta, BigDecimal monto) 
            throws CuentaInactivaException, SaldoInsuficienteException, LimiteExtraccionExcedidoException {
        
        if (!cuenta.isActiva()) {
            throw new CuentaInactivaException("La cuenta se encuentra inactiva.");
        }
        if (monto.compareTo(LIMITE_EXTRACCION) > 0) {
            throw new LimiteExtraccionExcedidoException("El monto supera el límite de $10,000 por operación.");
        }
        if (cuenta.getSaldo().compareTo(monto) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar la extracción.");
        }

        // Actualizar saldo
        cuenta.setSaldo(cuenta.getSaldo().subtract(monto));
        
        // Registrar transacción
        Transaccion t = new Transaccion(TipoTransaccion.EXTRACCION, monto.doubleValue(), "Extracción en cajero", cuenta.getSaldo().doubleValue());
        cuenta.agregarTransaccion(t);
    }
    
    // Aquí deberás agregar los métodos depositar(), transferir(), etc.
}