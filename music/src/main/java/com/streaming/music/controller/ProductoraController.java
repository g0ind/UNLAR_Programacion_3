
package com.streaming.music.controller;

import com.streaming.music.dto.ProductoraDTO;
import com.streaming.music.service.ProductoraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/productoras")
@RequiredArgsConstructor
@Tag(name = "Productoras", description = "Gestión de productoras/discográficas")
public class ProductoraController {

    private final ProductoraService productoraService;

    @GetMapping
    @Operation(summary = "Listar todas las productoras", 
               description = "Retorna una lista con todas las productoras registradas")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    public ResponseEntity<List<ProductoraDTO>> listarTodas() {
        return ResponseEntity.ok(productoraService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar productora por ID", 
               description = "Retorna una productora específica por su UUID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Productora encontrada"),
        @ApiResponse(responseCode = "404", description = "Productora no encontrada")
    })
    public ResponseEntity<ProductoraDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(productoraService.buscarPorId(id));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar productoras por nombre", 
               description = "Búsqueda parcial por nombre (case-insensitive)")
    @ApiResponse(responseCode = "200", description = "Búsqueda exitosa")
    public ResponseEntity<List<ProductoraDTO>> buscarPorNombre(
            @Parameter(description = "Nombre de la productora", example = "Universal")
            @RequestParam String nombre) {
        return ResponseEntity.ok(productoraService.buscarPorNombre(nombre));
    }

    @PostMapping
    @Operation(summary = "Crear nueva productora", 
               description = "Registra una nueva productora/discográfica")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Productora creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<ProductoraDTO> crearProductora(
            @Parameter(description = "Nombre de la productora", required = true, example = "Sony Music")
            @RequestParam @NotBlank String nombre,
            @Parameter(description = "País de origen", example = "EE.UU.")
            @RequestParam(required = false) String pais,
            @Parameter(description = "Año de fundación", example = "1929")
            @RequestParam(required = false) @PositiveOrZero Integer anioFundacion) {
        
        ProductoraDTO nueva = productoraService.crearProductora(nombre, pais, anioFundacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar productora", 
               description = "Actualiza los datos de una productora existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Productora actualizada"),
        @ApiResponse(responseCode = "404", description = "Productora no encontrada")
    })
    public ResponseEntity<ProductoraDTO> actualizarProductora(
            @PathVariable UUID id,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String pais,
            @RequestParam(required = false) @PositiveOrZero Integer anioFundacion) {
        
        return ResponseEntity.ok(productoraService.actualizarProductora(id, nombre, pais, anioFundacion));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar productora", 
               description = "Elimina una productora de la plataforma")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Productora eliminada"),
        @ApiResponse(responseCode = "404", description = "Productora no encontrada")
    })
    public ResponseEntity<Void> eliminarProductora(@PathVariable UUID id) {
        productoraService.eliminarProductora(id);
        return ResponseEntity.noContent().build();
    }
}