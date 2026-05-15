package com.streaming.music.controller;

import com.streaming.music.dto.AlbumDTO;
import com.streaming.music.service.AlbumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/albumes")
@RequiredArgsConstructor
@Tag(name = "Álbumes", description = "Gestión de álbumes musicales")
public class AlbumController {  // ← Debe ser AlbumController, NO CancionController

    private final AlbumService albumService;

    @GetMapping
    @Operation(summary = "Listar todos los álbumes", 
               description = "Retorna una lista con todos los álbumes registrados")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    public ResponseEntity<List<AlbumDTO>> listarTodos() {
        return ResponseEntity.ok(albumService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar álbum por ID", 
               description = "Retorna un álbum específico por su UUID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Álbum encontrado"),
        @ApiResponse(responseCode = "404", description = "Álbum no encontrado")
    })
    public ResponseEntity<AlbumDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(albumService.buscarPorId(id));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar álbumes por título", 
               description = "Búsqueda parcial por título (case-insensitive)")
    @ApiResponse(responseCode = "200", description = "Búsqueda exitosa")
    public ResponseEntity<List<AlbumDTO>> buscarPorTitulo(
            @Parameter(description = "Título del álbum", example = "Abbey Road")
            @RequestParam String titulo) {
        return ResponseEntity.ok(albumService.buscarPorTitulo(titulo));
    }

    @PostMapping
    @Operation(summary = "Crear nuevo álbum", 
               description = "Registra un nuevo álbum en la plataforma")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Álbum creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Artista o productora no encontrados")
    })
    public ResponseEntity<AlbumDTO> crearAlbum(
            @Parameter(description = "Título del álbum", required = true, example = "Dark Side of the Moon")
            @RequestParam @NotBlank String titulo,
            @Parameter(description = "ID del artista", required = true)
            @RequestParam UUID artistaId,
            @Parameter(description = "ID de la productora", required = true)
            @RequestParam UUID productoraId,
            @Parameter(description = "Fecha de lanzamiento", required = true, example = "1973-03-01")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaLanzamiento) {
        
        AlbumDTO nuevo = albumService.crearAlbum(titulo, artistaId, productoraId, fechaLanzamiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar álbum", 
               description = "Actualiza los datos de un álbum existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Álbum actualizado"),
        @ApiResponse(responseCode = "404", description = "Álbum no encontrado")
    })
    public ResponseEntity<AlbumDTO> actualizarAlbum(
            @PathVariable UUID id,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaLanzamiento) {
        
        return ResponseEntity.ok(albumService.actualizarAlbum(id, titulo, fechaLanzamiento));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar álbum", 
               description = "Elimina un álbum de la plataforma")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Álbum eliminado"),
        @ApiResponse(responseCode = "404", description = "Álbum no encontrado")
    })
    public ResponseEntity<Void> eliminarAlbum(@PathVariable UUID id) {
        albumService.eliminarAlbum(id);
        return ResponseEntity.noContent().build();
    }
}