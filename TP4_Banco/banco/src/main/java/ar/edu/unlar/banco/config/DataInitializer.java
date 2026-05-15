package ar.edu.unlar.banco.config;

import ar.edu.unlar.banco.model.CajaAhorro;
import ar.edu.unlar.banco.model.Cliente;
import jakarta.annotation.PostConstruct;

public class DataInitializer {
    private final CajaAhorro cajaAhorro;

    public DataInitializer(CajaAhorro cajaAhorro) {
        this.cajaAhorro = cajaAhorro;
    }
    @PostConstruct
    public void init() {
        Cliente cliente1 = new Cliente();
        Cliente cliente2 = new Cliente();

        cajaAhorro.agregarCajaAhorro(new CajaAhorro("CBU123", 1000.0, cliente1));
        cajaAhorro.agregarCajaAhorro(new CajaAhorro("CBU456", 2000.0, cliente2));
    }
}
