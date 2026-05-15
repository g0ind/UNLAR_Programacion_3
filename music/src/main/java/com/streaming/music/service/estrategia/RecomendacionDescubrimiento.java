package com.streaming.music.service.estrategia;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.streaming.music.model.Cancion;

@Component
public class RecomendacionDescubrimiento implements EstrategiaRecomendacion {
    
    private static final int MAX_REPRODUCCIONES = 1000;
    private static final int MAX_ANIOS_ANTIGUEDAD = 2;
    
    @Override
    public List<Cancion> recomendar(List<Cancion> catalogo, Cancion cancionBase) {
        LocalDate fechaLimite = LocalDate.now().minusYears(MAX_ANIOS_ANTIGUEDAD);
        
        return catalogo.stream()
                .filter(c -> !c.getId().equals(cancionBase.getId()))
                .filter(c -> c.getReproducciones() < MAX_REPRODUCCIONES)
                .filter(c -> c.getFechaLanzamiento().isAfter(fechaLimite))
                .filter(c -> c.getGenero() != cancionBase.getGenero())
                .sorted(Comparator.comparingDouble(Cancion::getRating).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }
}