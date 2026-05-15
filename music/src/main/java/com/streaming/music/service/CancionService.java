package com.streaming.music.service;

import com.streaming.music.dto.*;
import com.streaming.music.exception.DuracionExactaNoEncontradaException;
import com.streaming.music.exception.ResourceNotFoundException;
import com.streaming.music.model.*;
import com.streaming.music.repository.*;
import com.streaming.music.service.estrategia.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CancionService {

    private final CancionRepository cancionRepository;
    private final ArtistaRepository artistaRepository;
    private final AlbumRepository albumRepository;
    // Estrategias inyectadas
    private final RecomendacionPorGenero recomendacionPorGenero;
    private final RecomendacionPorPopularidad recomendacionPorPopularidad;
    private final RecomendacionDescubrimiento recomendacionDescubrimiento;

    // ==================== CRUD BÁSICO ====================

    public List<CancionDTO> listarTodas() {
        return cancionRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public CancionDTO buscarPorId(UUID id) {
        Cancion cancion = cancionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Canción no encontrada con ID: " + id));
        return toDTO(cancion);
    }

    public CancionDTO crearCancion(String titulo, UUID artistaId, UUID albumId,
            Genero genero, int duracionSegundos,
            double rating, LocalDate fechaLanzamiento) {

        // Validar que el artista existe
        artistaRepository.findById(artistaId)
                .orElseThrow(() -> new ResourceNotFoundException("Artista no encontrado"));

        // Validar que el álbum existe
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Álbum no encontrado"));

        Cancion cancion = new Cancion(titulo, artistaId, albumId, genero,
                duracionSegundos, rating, fechaLanzamiento);

        Cancion saved = cancionRepository.save(cancion);
        album.agregarCancion(saved.getId());
        albumRepository.update(album);

        return toDTO(saved);
    }

    public void eliminarCancion(UUID id) {
        if (!cancionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Canción no encontrada con ID: " + id);
        }
        cancionRepository.deleteById(id);
    }

    public CancionDTO incrementarReproducciones(UUID id) {
        Cancion cancion = cancionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Canción no encontrada con ID: " + id));

        cancionRepository.update(cancion);

        return toDTO(cancion);
    }

    // ==================== STREAMS API (30%) ====================

    /**
     * Filtrado compuesto con Streams API
     */
    public List<CancionDTO> filtrarConStreams(Genero genero, Integer ratingMinimo, Integer duracionMaxima) {
        return cancionRepository.findAll().stream()
                .filter(c -> genero == null || c.getGenero() == genero)
                .filter(c -> ratingMinimo == null || c.getRating() >= ratingMinimo)
                .filter(c -> duracionMaxima == null || c.getDuracionSegundos() <= duracionMaxima)
                .map(this::toDTO)
                .toList();
    }

    /**
     * Top 10 más reproducidas
     */
    public List<CancionDTO> top10MasReproducidas() {
        return cancionRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Cancion::getReproducciones).reversed())
                .limit(10)
                .map(this::toDTO)
                .toList();
    }

    /**
     * Promedio de duración por género
     */
    public Map<String, Double> promedioDuracionPorGenero() {
        return cancionRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        c -> c.getGenero().getDisplayName(),
                        Collectors.averagingInt(Cancion::getDuracionSegundos)));
    }

    /**
     * Artista más popular (maxBy reproducciones sumando todas sus canciones)
     */
    public Map<String, Object> artistaMasPopular() {
        Map<UUID, Long> reproduccionesPorArtista = cancionRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Cancion::getArtistaId,
                        Collectors.summingLong(Cancion::getReproducciones)));

        return reproduccionesPorArtista.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> {
                    Artista artista = artistaRepository.findById(entry.getKey()).orElse(null);
                    return Map.<String, Object>of(
                            "artista", artista != null ? artista.getNombre() : "Desconocido",
                            "reproducciones", entry.getValue());
                })
                .orElse(Map.of("artista", "Sin datos", "reproducciones", 0L));
    }

    /**
     * Distribución por décadas
     */
    public Map<Integer, Long> distribucionPorDecadas() {
        return cancionRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        c -> (c.getFechaLanzamiento().getYear() / 10) * 10,
                        Collectors.counting()));
    }

    // ==================== ALGORITMOS DE BÚSQUEDA Y ORDENAMIENTO (20%)
    // ====================

    /**
     * Búsqueda lineal con predicados múltiples - Complejidad O(n)
     */
    public List<CancionDTO> busquedaLineal(Genero genero, Integer anoMinimo, Double ratingMinimo) {
        List<Cancion> resultados = new ArrayList<>();

        // Algoritmo de búsqueda lineal O(n)
        for (Cancion cancion : cancionRepository.findAll()) {
            boolean cumpleGenero = genero == null || cancion.getGenero() == genero;
            boolean cumpleAno = anoMinimo == null ||
                    cancion.getFechaLanzamiento().getYear() > anoMinimo;
            boolean cumpleRating = ratingMinimo == null ||
                    cancion.getRating() >= ratingMinimo;

            if (cumpleGenero && cumpleAno && cumpleRating) {
                resultados.add(cancion);
            }
        }

        return resultados.stream().map(this::toDTO).toList();
    }

    /**
     * Búsqueda binaria por título - Complejidad O(n log n + log n)
     * Primero ordena el catálogo, luego aplica búsqueda binaria
     */
    public Optional<CancionDTO> busquedaBinariaPorTitulo(String titulo) {
        List<Cancion> catalogoOrdenado = cancionRepository.findAll().stream()
                .sorted(Comparator.comparing(Cancion::getTitulo))
                .toList();

        int izquierda = 0;
        int derecha = catalogoOrdenado.size() - 1;

        while (izquierda <= derecha) {
            int medio = (izquierda + derecha) / 2;
            Cancion cancion = catalogoOrdenado.get(medio);
            int comparacion = cancion.getTitulo().compareToIgnoreCase(titulo);

            if (comparacion == 0) {
                return Optional.of(toDTO(cancion));
            } else if (comparacion < 0) {
                izquierda = medio + 1;
            } else {
                derecha = medio - 1;
            }
        }

        return Optional.empty();
    }

    /**
     * Ordenamiento personalizado: por artista, luego fecha lanzamiento descendente
     */
    public List<CancionDTO> ordenarPersonalizado() {
        return cancionRepository.findAll().stream()
                .sorted(Comparator
                        .comparing((Cancion c) -> {
                            Artista artista = artistaRepository.findById(c.getArtistaId()).orElse(null);
                            return artista != null ? artista.getNombre() : "";
                        })
                        .thenComparing(Cancion::getFechaLanzamiento)
                        .reversed())
                .map(this::toDTO)
                .toList();
    }

    // ==================== PLAYLIST AUTOMÁTICA (Mochila/Subset Sum)
    // ====================

    /**
     * Playlist que sume EXACTAMENTE X minutos
     * Algoritmo recursivo de subset sum - Complejidad O(2^n)
     */
    public List<CancionDTO> playlistExacta(int minutosObjetivo) {
        int segundosObjetivo = minutosObjetivo * 60;
        List<Cancion> catalogo = cancionRepository.findAll();

        List<Cancion> resultado = encontrarCombinacionExacta(catalogo, segundosObjetivo);

        if (resultado == null || resultado.isEmpty()) {
            throw new DuracionExactaNoEncontradaException(
                    String.format("No se encontró combinación exacta para %d minutos", minutosObjetivo));
        }

        return resultado.stream().map(this::toDTO).toList();
    }

    /**
     * Algoritmo recursivo de subset sum
     * 
     * @param canciones         Lista de canciones disponibles
     * @param segundosRestantes Segundos que faltan para cumplir el objetivo
     * @return Lista de canciones que suman exactamente el tiempo, o null si no hay
     *         solución
     */
    private List<Cancion> encontrarCombinacionExacta(List<Cancion> canciones, int segundosRestantes) {
        // Caso base: solución encontrada
        if (segundosRestantes == 0) {
            return new ArrayList<>();
        }

        // Caso base: no hay solución
        if (segundosRestantes < 0 || canciones.isEmpty()) {
            return null;
        }

        Cancion primera = canciones.get(0);
        List<Cancion> resto = canciones.subList(1, canciones.size());

        // Opción 1: INCLUIR la primera canción
        List<Cancion> incluyendo = encontrarCombinacionExacta(resto,
                segundosRestantes - primera.getDuracionSegundos());
        if (incluyendo != null) {
            incluyendo.add(0, primera);
            return incluyendo;
        }

        // Opción 2: EXCLUIR la primera canción
        return encontrarCombinacionExacta(resto, segundosRestantes);
    }

    // ==================== PATRON STRATEGY (20%) ====================

    public List<CancionDTO> recomendarPorGenero(UUID cancionId) {
        Cancion base = cancionRepository.findById(cancionId)
                .orElseThrow(() -> new ResourceNotFoundException("Canción no encontrada"));

        List<Cancion> catalogo = cancionRepository.findAll();

        return recomendacionPorGenero.recomendar(catalogo, base).stream()
                .map(this::toDTO)
                .toList();
    }

    public List<CancionDTO> recomendarPorPopularidad(UUID cancionId) {
        Cancion base = cancionRepository.findById(cancionId)
                .orElseThrow(() -> new ResourceNotFoundException("Canción no encontrada"));

        List<Cancion> catalogo = cancionRepository.findAll();

        return recomendacionPorPopularidad.recomendar(catalogo, base).stream()
                .map(this::toDTO)
                .toList();
    }

    public List<CancionDTO> recomendarDescubrimiento(UUID cancionId) {
        Cancion base = cancionRepository.findById(cancionId)
                .orElseThrow(() -> new ResourceNotFoundException("Canción no encontrada"));

        List<Cancion> catalogo = cancionRepository.findAll();

        return recomendacionDescubrimiento.recomendar(catalogo, base).stream()
                .map(this::toDTO)
                .toList();
    }

    // ==================== MÉTODOS AUXILIARES ====================

    private CancionDTO toDTO(Cancion cancion) {
        String artistaNombre = artistaRepository.findById(cancion.getArtistaId())
                .map(Artista::getNombre)
                .orElse("Desconocido");

        String albumTitulo = albumRepository.findById(cancion.getAlbumId())
                .map(Album::getTitulo)
                .orElse("Desconocido");

        return CancionDTO.fromCancion(cancion, artistaNombre, albumTitulo);
    }
}