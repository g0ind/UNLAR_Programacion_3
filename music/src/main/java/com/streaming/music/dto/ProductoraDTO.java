package com.streaming.music.dto;

import com.streaming.music.model.Productora;
import java.util.UUID;

public record ProductoraDTO(
    UUID id,
    String nombre,
    String pais,
    Integer anioFundacion,
    Integer cantidadAlbumes
) {
    public static ProductoraDTO fromProductora(Productora productora, int cantidadAlbumes) {
        return new ProductoraDTO(
            productora.getId(),
            productora.getNombre(),
            productora.getPais(),
            productora.getAnioFundacion(),
            cantidadAlbumes
        );
    }
}