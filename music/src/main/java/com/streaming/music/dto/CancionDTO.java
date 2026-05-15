package com.streaming.music.dto;

import com.streaming.music.model.Genero;
import java.time.LocalDate;
import java.util.UUID;

public record CancionDTO(
    UUID id,
    String titulo,
    String artistaNombre,
    String albumTitulo,
    Genero genero,
    int duracionSegundos,
    int duracionMinutos,
    int reproducciones,
    double rating,
    LocalDate fechaLanzamiento,
    int anio
) {
    public static CancionDTO fromCancion(
        com.streaming.music.model.Cancion cancion,
        String artistaNombre,
        String albumTitulo
    ) {
        return new CancionDTO(
            cancion.getId(),
            cancion.getTitulo(),
            artistaNombre,
            albumTitulo,
            cancion.getGenero(),
            cancion.getDuracionSegundos(),
            cancion.getDuracionSegundos() / 60,
            cancion.getReproducciones(),
            cancion.getRating(),
            cancion.getFechaLanzamiento(),
            cancion.getFechaLanzamiento().getYear()
        );
    }
}