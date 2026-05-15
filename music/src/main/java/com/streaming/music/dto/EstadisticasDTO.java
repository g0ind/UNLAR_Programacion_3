package com.streaming.music.dto;

import java.util.Map;

public record EstadisticasDTO(
    Map<String, Double> promedioDuracionPorGenero,
    String artistaMasPopular,
    long reproduccionesArtistaMasPopular,
    Map<Integer, Long> distribucionPorDecadas
) {}