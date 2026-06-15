package com.inventory.smart.controller;

import com.inventory.smart.dto.CategoriaRequest;
import com.inventory.smart.dto.CategoriaResponse;
import com.inventory.smart.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

/**
 * Controlador REST para gestionar operaciones con categorías.
 *
 * <p>Permite altas, bajas, modificaciones y consultas del catálogo de categorías.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    /**
     * Inyección por constructor del servicio de categorías.
     *
     * @param categoriaService el servicio de categorías
     */
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    /**
     * Lista todas las categorías registradas en el sistema.
     *
     * @return lista completa de categorías y estado HTTP 200 OK
     */
    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listarTodas() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    /**
     * Busca los detalles de una categoría por su ID.
     *
     * @param id el identificador único de la categoría
     * @return la categoría encontrada y estado HTTP 200 OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.findById(id));
    }

    /**
     * Crea una nueva categoría.
     *
     * @param request datos para la nueva categoría a crear
     * @return la categoría creada y estado HTTP 201 Created con Location
     */
    @PostMapping
    public ResponseEntity<CategoriaResponse> crear(@Valid @RequestBody CategoriaRequest request) {
        CategoriaResponse response = categoriaService.crear(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    /**
     * Actualiza los datos de una categoría existente.
     *
     * @param id      el identificador de la categoría a actualizar
     * @param request los nuevos datos de la categoría
     * @return la categoría modificada y estado HTTP 200 OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequest request) {
        return ResponseEntity.ok(categoriaService.actualizar(id, request));
    }

    /**
     * Elimina una categoría por su ID.
     *
     * <p>Retornará un error de conflicto si existen productos asociados a ella.</p>
     *
     * @param id el identificador de la categoría a eliminar
     * @return respuesta vacía con código HTTP 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        categoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
