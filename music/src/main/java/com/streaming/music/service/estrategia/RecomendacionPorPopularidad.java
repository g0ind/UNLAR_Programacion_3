package com.streaming.music.service.estrategia;

import com.streaming.music.model.Cancion;
import org.springframework.stereotype.Component;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecomendacionPorPopularidad implements EstrategiaRecomendacion {
    
    @Override
    public List<Cancion> recomendar(List<Cancion> catalogo, Cancion cancionBase) {
        return catalogo.stream()
                .filter(c -> !c.getId().equals(cancionBase.getId())) // Excluir la canción base
                .sorted(Comparator.comparingInt(Cancion::getReproducciones).reversed())
                .limit(5) // Top 5 global
                .collect(Collectors.toList());
    }
}