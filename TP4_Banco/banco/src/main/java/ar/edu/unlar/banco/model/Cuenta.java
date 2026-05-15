package ar.edu.unlar.banco.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Cuenta {
    protected String cbu;
    protected double saldo;
    protected Cliente cliente;
    protected List<Movimiento> movimientos;

    public Cuenta(String cbu, double saldo, Cliente cliente) {
        this.cbu = cbu;
        this.saldo = saldo;
        this.cliente = cliente;
        this.movimientos = new ArrayList<>();
    }

    public abstract void depositar(Double monto);
    public abstract void extraer(Double monto);
    public abstract Double consultarSaldo();
}
