package ar.edu.unlar.banco.service;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unlar.banco.model.CajaAhorro;

public class CajaAhorroService {
    private List<CajaAhorro> cajasAhorro = new ArrayList<>();

    private List<CajaAhorro> listCajasAhorro() {
        return cajasAhorro;
    }
    private CajaAhorro getCajaAhorroById(String cbu) {
        return cajasAhorro.stream()
                .filter(caja -> caja.getClass().equals(cbu))
                .findFirst()
                .orElse(null);
                
    }
}

