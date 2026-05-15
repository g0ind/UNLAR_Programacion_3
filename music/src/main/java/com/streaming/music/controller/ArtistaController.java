package com.streaming.music.controller;

import com.streaming.music.dto.ArtistaDTO;
import com.streaming.music.service.ArtistaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/artistas")
@RequiredArgsConstructor
@Tag(name = "Artistas", description = "Gestión de artistas musicales")
public class ArtistaController {

    private final ArtistaService artistaService;

    @GetMapping
    @Operation(summary = "Listar todos los artistas", 
               description = "Retorna una lista con todos los artistas registrados")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    public ResponseEntity<List<ArtistaDTO>> listarTodos() {
        return ResponseEntity.ok(artistaService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar artista por ID", 
               description = "Retorna un artista específico por su UUID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Artista encontrado"),
        @ApiResponse(responseCode = "404", description = "Artista no encontrado")
    })
    public ResponseEntity<ArtistaDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(artistaService.buscarPorId(id));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar artistas por nombre", 
               description = "Búsqueda parcial por nombre (case-insensitive)")
    @ApiResponse(responseCode = "200", description = "Búsqueda exitosa")
    public ResponseEntity<List<ArtistaDTO>> buscarPorNombre(
            @Parameter(description = "Nombre del artista a buscar", example = "Queen")
            @RequestParam String nombre) {
        return ResponseEntity.ok(artistaService.buscarPorNombre(nombre));
    }

    @PostMapping
    @Operation(summary = "Crear nuevo artista", 
               description = "Registra un nuevo artista en la plataforma")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Artista creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<ArtistaDTO> crearArtista(
            @Parameter(description = "Nombre del artista", required = true, example = "Pink Floyd")
            @RequestParam @NotBlank String nombre,
            @Parameter(description = "País de origen", example = "Reino Unido")
            @RequestParam(required = false) String pais,
            @Parameter(description = "Año de formación", example = "1965")
            @RequestParam(required = false) @PositiveOrZero Integer anioFormacion) {
        
        ArtistaDTO nuevo = artistaService.crearArtista(nombre, pais, anioFormacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar artista", 
               description = "Actualiza los datos de un artista existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Artista actualizado"),
        @ApiResponse(responseCode = "404", description = "Artista no encontrado")
    })
    public ResponseEntity<ArtistaDTO> actualizarArtista(
            @PathVariable UUID id,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String pais,
            @RequestParam(required = false) @PositiveOrZero Integer anioFormacion) {
        
        return ResponseEntity.ok(artistaService.actualizarArtista(id, nombre, pais, anioFormacion));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar artista", 
               description = "Elimina un artista de la plataforma")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Artista eliminado"),
        @ApiResponse(responseCode = "404", description = "Artista no encontrado")
    })
    public ResponseEntity<Void> eliminarArtista(@PathVariable UUID id) {
        artistaService.eliminarArtista(id);
        return ResponseEntity.noContent().build();
    }
}