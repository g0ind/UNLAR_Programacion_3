package unlar.edu.ar.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CuentaBancaria {
    private final String numeroCuenta; // Inmutable
    private BigDecimal saldo;
    private String titular;
    private boolean activa;
    private List<Transaccion> historialTransacciones;

    public CuentaBancaria(String numeroCuenta, String titular, BigDecimal saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldoInicial;
        this.activa = true;
        this.historialTransacciones = new ArrayList<>();
    }

    public String getNumeroCuenta() { return numeroCuenta; }
    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }
    public String getTitular() { return titular; }
    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
    public List<Transaccion> getHistorialTransacciones() { return historialTransacciones; }

    public void agregarTransaccion(Transaccion t) {
        this.historialTransacciones.add(t);
    }
}