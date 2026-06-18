package ar.edu.unlar.prog3.tp_comparable_comparator.dto;

import java.util.Set;

public record ErrorResponse(
        String error,
        String criterioRecibido,
        Set<String> criteriosAceptados
) {
}
