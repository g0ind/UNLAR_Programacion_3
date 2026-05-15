package com.streaming.music.service.estrategia;

import com.streaming.music.model.Cancion;
import java.util.List;

/**
 * Patrón Strategy - Interfaz para algoritmos de recomendación
 * 
 * Las estrategias concretas implementan diferentes formas de recomendar canciones:
 * - RecomendacionPorGenero: canciones del mismo género
 * - RecomendacionPorPopularidad: canciones más populares globalmente
 * - RecomendacionDescubrimiento: canciones nuevas y poco conocidas
 */
@FunctionalInterface
public interface EstrategiaRecomendacion {
    
    /**
     * Genera una lista de canciones recomendadas basadas en una canción base
     * 
     * @param catalogo Lista completa de canciones disponibles
     * @param cancionBase Canción a partir de la cual se generan recomendaciones
     * @return Lista de canciones recomendadas (ordenadas por relevancia)
     */
    List<Cancion> recomendar(List<Cancion> catalogo, Cancion cancionBase);
}