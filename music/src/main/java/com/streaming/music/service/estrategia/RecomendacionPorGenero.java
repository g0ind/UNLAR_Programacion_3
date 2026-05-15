package com.streaming.music.service.estrategia;

import com.streaming.music.model.Cancion;
import org.springframework.stereotype.Component;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecomendacionPorGenero implements EstrategiaRecomendacion {
    
    @Override
    public List<Cancion> recomendar(List<Cancion> catalogo, Cancion cancionBase) {
        return catalogo.stream()
                .filter(c -> !c.getId().equals(cancionBase.getId())) // Excluir la canción base
                .filter(c -> c.getGenero() == cancionBase.getGenero()) // Mismo género
                .sorted(Comparator.comparingDouble(Cancion::getRating).reversed()) // Por rating
                .limit(10) // Top 10
                .collect(Collectors.toList());
    }
}