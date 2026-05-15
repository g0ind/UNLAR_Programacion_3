package ar.edu.unlar.banco.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CajaAhorro extends Cuenta {

    public CajaAhorro(String cbu, double saldo, Cliente cliente) {
        super(cbu, saldo, cliente);
    }

    @Override
    public void depositar(Double monto) {
        // Implementation for depositing money into savings account
    }

    @Override
    public void extraer(Double monto) {
        // Implementation for withdrawing money from savings account
    }

    @Override
    public Double consultarSaldo() {
        // Implementation for checking savings account balance
        return null;
    }

}
