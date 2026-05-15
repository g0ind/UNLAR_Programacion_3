package com.streaming.music.service;

import com.streaming.music.model.Cancion;
import com.streaming.music.repository.CancionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BusquedaService {

    private final CancionRepository cancionRepository;

    /**
     * Búsqueda lineal con múltiples predicados
     * Complejidad: O(n) - recorre todo el catálogo una vez
     */
    public List<Cancion> busquedaLineal(Optional<UUID> artistaId, 
                                         Optional<String> genero, 
                                         Optional<Integer> anioMinimo,
                                         Optional<Double> ratingMinimo) {
        return cancionRepository.findAll().stream()
                .filter(c -> artistaId.isEmpty() || c.getArtistaId().equals(artistaId.get()))
                .filter(c -> genero.isEmpty() || c.getGenero().name().equalsIgnoreCase(genero.get()))
                .filter(c -> anioMinimo.isEmpty() || c.getFechaLanzamiento().getYear() >= anioMinimo.get())
                .filter(c -> ratingMinimo.isEmpty() || c.getRating() >= ratingMinimo.get())
                .toList();
    }

    /**
     * Búsqueda binaria por título
     * Complejidad: O(log n) - requiere lista preordenada
     */
    public Optional<Cancion> busquedaBinariaPorTitulo(String titulo) {
        List<Cancion> cancionesOrdenadas = cancionRepository.findAll().stream()
                .sorted(Comparator.comparing(Cancion::getTitulo, String.CASE_INSENSITIVE_ORDER))
                .toList();
        
        int izquierda = 0;
        int derecha = cancionesOrdenadas.size() - 1;
        
        while (izquierda <= derecha) {
            int medio = izquierda + (derecha - izquierda) / 2;
            Cancion cancion = cancionesOrdenadas.get(medio);
            int comparacion = cancion.getTitulo().compareToIgnoreCase(titulo);
            
            if (comparacion == 0) {
                return Optional.of(cancion);
            } else if (comparacion < 0) {
                izquierda = medio + 1;
            } else {
                derecha = medio - 1;
            }
        }
        
        return Optional.empty();
    }

    /**
     * Ordenamiento personalizado: por artista y luego por fecha
     * Complejidad: O(n log n) - usando TimSort
     */
    public List<Cancion> ordenarPersonalizado() {
        return cancionRepository.findAll().stream()
                .sorted((c1, c2) -> {
                    // Primero por artista (simulado, normalmente vendría de otra fuente)
                    int artistaComp = c1.getArtistaId().toString()
                            .compareTo(c2.getArtistaId().toString());
                    if (artistaComp != 0) return artistaComp;
                    // Luego por fecha de lanzamiento (descendente)
                    return c2.getFechaLanzamiento().compareTo(c1.getFechaLanzamiento());
                })
                .toList();
    }
}