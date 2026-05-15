package com.streaming.music.controller;

import com.streaming.music.dto.CancionDTO;
import com.streaming.music.dto.EstadisticasDTO;
import com.streaming.music.exception.DuracionExactaNoEncontradaException;
import com.streaming.music.model.Genero;
import com.streaming.music.service.CancionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Canciones", description = "Endpoints para gestión de canciones")
public class CancionController {
    
    private final CancionService cancionService;
    
    @GetMapping
    @Operation(summary = "Listar todas las canciones", description = "Retorna todas las canciones del catálogo")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    public ResponseEntity<List<CancionDTO>> listarTodas() {
        return ResponseEntity.ok(cancionService.listarTodas());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar canción por ID", description = "Retorna una canción específica por su UUID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Canción encontrada"),
        @ApiResponse(responseCode = "404", description = "Canción no encontrada")
    })
    public ResponseEntity<CancionDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(cancionService.buscarPorId(id));
    }
    
    @GetMapping("/buscar")
    @Operation(summary = "Búsqueda con filtros compuestos (Streams API)",
               description = "Filtra canciones por género, rating mínimo y duración máxima")
    @ApiResponse(responseCode = "200", description = "Filtrado exitoso")
    public ResponseEntity<List<CancionDTO>> buscarFiltros(
            @RequestParam(required = false) Genero genero,
            @RequestParam(required = false) Integer ratingMinimo,
            @RequestParam(required = false) Integer duracionMaxima) {
        return ResponseEntity.ok(cancionService.filtrarConStreams(genero, ratingMinimo, duracionMaxima));
    }
    
    @GetMapping("/busqueda-lineal")
    @Operation(summary = "Búsqueda lineal con predicados múltiples",
               description = "Algoritmo O(n) que filtra por género AND año > X AND rating > Y")
    @ApiResponse(responseCode = "200", description = "Búsqueda exitosa")
    public ResponseEntity<List<CancionDTO>> busquedaLineal(
            @RequestParam(required = false) Genero genero,
            @RequestParam(required = false) Integer anoMinimo,
            @RequestParam(required = false) Double ratingMinimo) {
        return ResponseEntity.ok(cancionService.busquedaLineal(genero, anoMinimo, ratingMinimo));
    }
    
    @GetMapping("/busqueda-binaria")
    @Operation(summary = "Búsqueda binaria por título",
               description = "Algoritmo O(log n) que busca una canción por su título exacto")
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
    
    @PostMapping("/{id}/reproducir")
    @Operation(summary = "Registrar reproducción", 
               description = "Incrementa el contador de reproducciones de una canción (thread-safe)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reproducción registrada"),
        @ApiResponse(responseCode = "404", description = "Canción no encontrada")
    })
    public ResponseEntity<CancionDTO> reproducir(@PathVariable UUID id) {
        return ResponseEntity.ok(cancionService.incrementarReproducciones(id));
    }
    
    @GetMapping("/top10")
    @Operation(summary = "Top 10 más reproducidas", description = "Retorna las 10 canciones más escuchadas")
    @ApiResponse(responseCode = "200", description = "Top 10 obtenido")
    public ResponseEntity<List<CancionDTO>> top10() {
        return ResponseEntity.ok(cancionService.top10MasReproducidas());
    }
    
    @GetMapping("/playlist")
    @Operation(summary = "Playlist por duración exacta",
               description = "Genera una playlist cuyas canciones sumen EXACTAMENTE X minutos usando algoritmo recursivo de subset sum O(2^n)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Playlist generada exitosamente"),
        @ApiResponse(responseCode = "404", description = "No existe combinación exacta")
    })
    public ResponseEntity<?> playlistExacta(@RequestParam int minutos) {
        try {
            List<CancionDTO> playlist = cancionService.playlistExacta(minutos);
            int duracionTotal = playlist.stream().mapToInt(CancionDTO::duracionSegundos).sum();
            
            return ResponseEntity.ok(Map.of(
                "canciones", playlist,
                "duracionTotalMinutos", duracionTotal / 60.0,
                "duracionObjetivo", minutos,
                "cantidadCanciones", playlist.size()
            ));
        } catch (DuracionExactaNoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage(), "minutos", minutos));
        }
    }
    
    @GetMapping("/{id}/recomendaciones/genero")
    @Operation(summary = "Recomendaciones por género", description = "Estrategia: mismo género, ordenadas por rating")
    @ApiResponse(responseCode = "200", description = "Recomendaciones generadas")
    public ResponseEntity<List<CancionDTO>> recomendarPorGenero(@PathVariable UUID id) {
        return ResponseEntity.ok(cancionService.recomendarPorGenero(id));
    }
    
    @GetMapping("/recomendaciones/top5")
    @Operation(summary = "Recomendaciones por popularidad", description = "Estrategia: top 5 más reproducidas global")
    @ApiResponse(responseCode = "200", description = "Recomendaciones generadas")
    public ResponseEntity<List<CancionDTO>> recomendarPorPopularidad(@RequestParam UUID baseId) {
        return ResponseEntity.ok(cancionService.recomendarPorPopularidad(baseId));
    }
    
    @GetMapping("/{id}/recomendaciones/descubrimiento")
    @Operation(summary = "Recomendaciones descubrimiento", 
               description = "Estrategia: <1000 plays, <2 años, género diferente")
    @ApiResponse(responseCode = "200", description = "Recomendaciones generadas")
    public ResponseEntity<List<CancionDTO>> recomendarDescubrimiento(@PathVariable UUID id) {
        return ResponseEntity.ok(cancionService.recomendarDescubrimiento(id));
    }
    
    @GetMapping("/estadisticas/promedio-duracion")
    @Operation(summary = "Promedio de duración por género", description = "Usando Collectors.averagingInt")
    @ApiResponse(responseCode = "200", description = "Estadísticas calculadas")
    public ResponseEntity<Map<String, Double>> promedioDuracionPorGenero() {
        return ResponseEntity.ok(cancionService.promedioDuracionPorGenero());
    }
    
    @GetMapping("/estadisticas/artista-popular")
    @Operation(summary = "Artista más popular", description = "Usando maxBy por reproducciones totales")
    @ApiResponse(responseCode = "200", description = "Artista más popular encontrado")
    public ResponseEntity<Map<String, Object>> artistaMasPopular() {
        return ResponseEntity.ok(cancionService.artistaMasPopular());
    }
    
    @GetMapping("/estadisticas/distribucion-decadas")
    @Operation(summary = "Distribución por décadas", description = "Usando Collectors.groupingBy")
    @ApiResponse(responseCode = "200", description = "Distribución calculada")
    public ResponseEntity<Map<Integer, Long>> distribucionPorDecadas() {
        return ResponseEntity.ok(cancionService.distribucionPorDecadas());
    }
    
    @PostMapping
    @Operation(summary = "Crear nueva canción")
    @ApiResponse(responseCode = "201", description = "Canción creada exitosamente")
    public ResponseEntity<CancionDTO> crearCancion(
            @RequestParam String titulo,
            @RequestParam UUID artistaId,
            @RequestParam UUID albumId,
            @RequestParam Genero genero,
            @RequestParam int duracionSegundos,
            @RequestParam double rating,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaLanzamiento) {
        
        CancionDTO nueva = cancionService.crearCancion(titulo, artistaId, albumId, 
                                                        genero, duracionSegundos, 
                                                        rating, fechaLanzamiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar canción")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Canción eliminada"),
        @ApiResponse(responseCode = "404", description = "Canción no encontrada")
    })
    public ResponseEntity<Void> eliminarCancion(@PathVariable UUID id) {
        cancionService.eliminarCancion(id);
        return ResponseEntity.noContent().build();
    }
}