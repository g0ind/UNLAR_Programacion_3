package com.streaming.music.dto;

import com.streaming.music.model.Artista;
import java.util.UUID;

public record ArtistaDTO(
    UUID id,
    String nombre,
    String pais,
    Integer anioFormacion,
    Integer cantidadAlbumes
) {
    public static ArtistaDTO fromArtista(Artista artista, int cantidadAlbumes) {
        return new ArtistaDTO(
            artista.getId(),
            artista.getNombre(),
            artista.getPais(),
            artista.getAnioFormacion(),
            cantidadAlbumes
        );
    }
}