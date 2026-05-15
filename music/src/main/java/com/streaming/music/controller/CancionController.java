package com.streaming.music.controller;

import com.streaming.music.dto.CancionDTO;
import com.streaming.music.exception.DuracionExactaNoEncontradaException;
import com.streaming.music.model.Genero;
import com.streaming.music.service.CancionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/canciones")
@RequiredArgsConstructor
@Tag(name = "Canciones", description = "Gestión de canciones - CRUD, búsquedas, recomendaciones y estadísticas")
public class CancionController {

    private final CancionService cancionService;

    // ==================== CRUD BÁSICO ====================

    @GetMapping
    @Operation(summary = "Listar todas las canciones", 
               description = "Retorna todas las canciones del catálogo")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    public ResponseEntity<List<CancionDTO>> listarTodas() {
        return ResponseEntity.ok(cancionService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar canción por ID", 
               description = "Retorna una canción específica por su UUID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Canción encontrada"),
        @ApiResponse(responseCode = "404", description = "Canción no encontrada")
    })
    public ResponseEntity<CancionDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(cancionService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear nueva canción", 
               description = "Registra una nueva canción en el catálogo")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Canción creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Artista o álbum no encontrado")
    })
    public ResponseEntity<CancionDTO> crearCancion(
            @Parameter(description = "Título de la canción", required = true, example = "Bohemian Rhapsody")
            @RequestParam String titulo,
            @Parameter(description = "ID del artista", required = true)
            @RequestParam UUID artistaId,
            @Parameter(description = "ID del álbum", required = true)
            @RequestParam UUID albumId,
            @Parameter(description = "Género musical", required = true)
            @RequestParam Genero genero,
            @Parameter(description = "Duración en segundos", example = "355")
            @RequestParam @Positive int duracionSegundos,
            @Parameter(description = "Rating (0.0 - 5.0)", example = "4.9")
            @RequestParam @Min(0) @Max(5) double rating,
            @Parameter(description = "Fecha de lanzamiento", example = "1975-10-31")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaLanzamiento) {
        
        CancionDTO nueva = cancionService.crearCancion(titulo, artistaId, albumId, 
                                                        genero, duracionSegundos, 
                                                        rating, fechaLanzamiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar canción", 
               description = "Elimina una canción del catálogo")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Canción eliminada"),
        @ApiResponse(responseCode = "404", description = "Canción no encontrada")
    })
    public ResponseEntity<Void> eliminarCancion(@PathVariable UUID id) {
        cancionService.eliminarCancion(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/reproducir")
    @Operation(summary = "Registrar reproducción", 
               description = "Incrementa el contador de reproducciones de una canción (thread-safe con AtomicInteger)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reproducción registrada"),
        @ApiResponse(responseCode = "404", description = "Canción no encontrada")
    })
    public ResponseEntity<CancionDTO> reproducir(@PathVariable UUID id) {
        return ResponseEntity.ok(cancionService.incrementarReproducciones(id));
    }

    // ==================== STREAMS API ====================

    @GetMapping("/buscar")
    @Operation(summary = "Búsqueda con filtros compuestos (Streams API)",
               description = "Filtra canciones por género, rating mínimo y duración máxima")
    @ApiResponse(responseCode = "200", description = "Filtrado exitoso")
    public ResponseEntity<List<CancionDTO>> buscarFiltros(
            @Parameter(description = "Género musical (ROCK, POP, JAZZ, ELECTRONICA, CLASICA)")
            @RequestParam(required = false) Genero genero,
            @Parameter(description = "Rating mínimo (0-5)", example = "4.0")
            @RequestParam(required = false) @Min(0) @Max(5) Integer ratingMinimo,
            @Parameter(description = "Duración máxima en segundos", example = "300")
            @RequestParam(required = false) @Positive Integer duracionMaxima) {
        return ResponseEntity.ok(cancionService.filtrarConStreams(genero, ratingMinimo, duracionMaxima));
    }

    @GetMapping("/top10")
    @Operation(summary = "Top 10 más reproducidas", 
               description = "Retorna las 10 canciones más escuchadas globalmente")
    @ApiResponse(responseCode = "200", description = "Top 10 obtenido")
    public ResponseEntity<List<CancionDTO>> top10() {
        return ResponseEntity.ok(cancionService.top10MasReproducidas());
    }

    // ==================== ESTADÍSTICAS ====================

    @GetMapping("/estadisticas/promedio-duracion")
    @Operation(summary = "Promedio de duración por género", 
               description = "Calcula el promedio de duración en segundos agrupado por género (Collectors.averagingInt)")
    @ApiResponse(responseCode = "200", description = "Estadísticas calculadas")
    public ResponseEntity<Map<String, Double>> promedioDuracionPorGenero() {
        return ResponseEntity.ok(cancionService.promedioDuracionPorGenero());
    }

    @GetMapping("/estadisticas/artista-popular")
    @Operation(summary = "Artista más popular", 
               description = "Determina el artista con más reproducciones totales (Collectors.maxBy)")
    @ApiResponse(responseCode = "200", description = "Artista más popular encontrado")
    public ResponseEntity<Map<String, Object>> artistaMasPopular() {
        return ResponseEntity.ok(cancionService.artistaMasPopular());
    }

    @GetMapping("/estadisticas/distribucion-decadas")
    @Operation(summary = "Distribución por décadas", 
               description = "Agrupa canciones por década de lanzamiento (Collectors.groupingBy)")
    @ApiResponse(responseCode = "200", description = "Distribución calculada")
    public ResponseEntity<Map<Integer, Long>> distribucionPorDecadas() {
        return ResponseEntity.ok(cancionService.distribucionPorDecadas());
    }

    // ==================== ALGORITMOS DE BÚSQUEDA Y ORDENAMIENTO ====================

    @GetMapping("/busqueda-lineal")
    @Operation(summary = "Búsqueda lineal con predicados múltiples",
               description = "Algoritmo O(n) que filtra por género AND año > X AND rating > Y")
    @ApiResponse(responseCode = "200", description = "Búsqueda exitosa")
    public ResponseEntity<List<CancionDTO>> busquedaLineal(
            @Parameter(description = "Género musical")
            @RequestParam(required = false) Genero genero,
            @Parameter(description = "Año mínimo (mayor que)", example = "2000")
            @RequestParam(required = false) Integer anoMinimo,
            @Parameter(description = "Rating mínimo (0-5)", example = "4.0")
            @RequestParam(required = false) @Min(0) @Max(5) Double ratingMinimo) {
        return ResponseEntity.ok(cancionService.busquedaLineal(genero, anoMinimo, ratingMinimo));
    }

    @GetMapping("/busqueda-binaria")
    @Operation(summary = "Búsqueda binaria por título",
               description = "Algoritmo O(log n) que busca una canción por su título exacto (requiere lista ordenada)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Canción encontrada"),
        @ApiResponse(responseCode = "404", description = "Canción no encontrada")
    })
    public ResponseEntity<?> busquedaBinaria(@RequestParam String titulo) {
        return cancionService.busquedaBinariaPorTitulo(titulo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Canción no encontrada con título: " + titulo)));
    }

    @GetMapping("/ordenar")
    @Operation(summary = "Ordenamiento personalizado",
               description = "Ordena canciones por artista y luego por fecha de lanzamiento descendente")
    @ApiResponse(responseCode = "200", description = "Ordenamiento exitoso")
    public ResponseEntity<List<CancionDTO>> ordenarPersonalizado() {
        return ResponseEntity.ok(cancionService.ordenarPersonalizado());
    }

    // ==================== PLAYLIST AUTOMÁTICA ====================

    @GetMapping("/playlist")
    @Operation(summary = "Playlist por duración exacta (Problema de la Mochila)",
               description = "Genera una playlist cuyas canciones sumen EXACTAMENTE X minutos usando algoritmo recursivo de subset sum O(2^n)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Playlist generada exitosamente"),
        @ApiResponse(responseCode = "404", description = "No existe combinación exacta")
    })
    public ResponseEntity<?> playlistExacta(@RequestParam @Positive int minutos) {
        try {
            List<CancionDTO> playlist = cancionService.playlistExacta(minutos);
            int duracionTotal = playlist.stream().mapToInt(CancionDTO::duracionSegundos).sum();
            
            return ResponseEntity.ok(Map.of(
                "canciones", playlist,
                "duracionTotalMinutos", duracionTotal / 60.0,
                "duracionObjetivo", minutos,
                "cantidadCanciones", playlist.size(),
                "algoritmo", "Subset Sum Recursivo O(2^n)"
            ));
        } catch (DuracionExactaNoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage(), "minutos", minutos));
        }
    }

    // ==================== PATRÓN STRATEGY - RECOMENDACIONES ====================

    @GetMapping("/{id}/recomendaciones/genero")
    @Operation(summary = "Recomendaciones por género (Strategy)",
               description = "Estrategia: mismo género que la canción base, ordenadas por rating descendente")
    @ApiResponse(responseCode = "200", description = "Recomendaciones generadas")
    public ResponseEntity<List<CancionDTO>> recomendarPorGenero(@PathVariable UUID id) {
        return ResponseEntity.ok(cancionService.recomendarPorGenero(id));
    }

    @GetMapping("/recomendaciones/top5")
    @Operation(summary = "Recomendaciones por popularidad (Strategy)",
               description = "Estrategia: top 5 canciones más reproducidas globalmente (excluyendo la base)")
    @ApiResponse(responseCode = "200", description = "Recomendaciones generadas")
    public ResponseEntity<List<CancionDTO>> recomendarPorPopularidad(
            @Parameter(description = "ID de la canción base a excluir")
            @RequestParam UUID baseId) {
        return ResponseEntity.ok(cancionService.recomendarPorPopularidad(baseId));
    }

    @GetMapping("/{id}/recomendaciones/descubrimiento")
    @Operation(summary = "Recomendaciones descubrimiento (Strategy)",
               description = "Estrategia: canciones con <1000 reproducciones, <2 años de antigüedad y género diferente")
    @ApiResponse(responseCode = "200", description = "Recomendaciones generadas")
    public ResponseEntity<List<CancionDTO>> recomendarDescubrimiento(@PathVariable UUID id) {
        return ResponseEntity.ok(cancionService.recomendarDescubrimiento(id));
    }
}