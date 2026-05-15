package com.streaming.music.dto;

import com.streaming.music.model.Album;
import java.time.LocalDate;
import java.util.UUID;

public record AlbumDTO(
    UUID id,
    String titulo,
    String artistaNombre,
    String productoraNombre,
    LocalDate fechaLanzamiento,
    Integer cantidadCanciones
) {
    public static AlbumDTO fromAlbum(Album album, String artistaNombre, String productoraNombre, int cantidadCanciones) {
        return new AlbumDTO(
            album.getId(),
            album.getTitulo(),
            artistaNombre,
            productoraNombre,
            album.getFechaLanzamiento(),
            cantidadCanciones
        );
    }
}