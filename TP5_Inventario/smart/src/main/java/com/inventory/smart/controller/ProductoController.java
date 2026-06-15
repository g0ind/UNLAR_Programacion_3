package com.inventory.smart.controller;

import com.inventory.smart.dto.ProductoRequest;
import com.inventory.smart.dto.ProductoResponse;
import com.inventory.smart.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

/**
 * Controlador REST para gestionar operaciones asociadas con productos.
 *
 * <p>Expone endpoints para listado, filtrado, búsqueda, creación, actualización y eliminación.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    /**
     * Inyección por constructor del servicio de productos.
     *
     * @param productoService el servicio de productos
     */
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * Recupera todos los productos, aplicando filtros opcionales de búsqueda.
     *
     * @param categoria el identificador de la categoría de filtro (opcional)
     * @param precioMin precio mínimo de filtro (opcional)
     * @param precioMax precio máximo de filtro (opcional)
     * @param enStock   si es true, filtra solo productos con stock > 0 (opcional)
     * @return lista de productos coincidentes con los filtros
     */
    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listarTodos(
            @RequestParam(required = false) Long categoria,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax,
            @RequestParam(required = false) Boolean enStock) {
        return ResponseEntity.ok(productoService.findAll(categoria, precioMin, precioMax, enStock));
    }

    /**
     * Obtiene los detalles de un producto específico por su ID.
     *
     * @param id el identificador del producto
     * @return el producto encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    /**
     * Crea un nuevo producto y devuelve su ubicación.
     *
     * @param request datos del nuevo producto a crear
     * @return el producto creado y código HTTP 201 Created
     */
    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest request) {
        ProductoResponse response = productoService.crear(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    /**
     * Actualiza un producto existente.
     *
     * @param id      el identificador del producto a actualizar
     * @param request los nuevos datos del producto
     * @return el producto actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.ok(productoService.actualizar(id, request));
    }

    /**
     * Elimina un producto por su ID.
     *
     * @param id el identificador del producto a eliminar
     * @return respuesta vacía con código HTTP 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Búsqueda de productos que coincidan textualmente con su nombre.
     *
     * @param q la consulta de búsqueda
     * @return lista de productos coincidentes
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoResponse>> buscar(@RequestParam String q) {
        return ResponseEntity.ok(productoService.buscarPorNombre(q));
    }

    /**
     * Devuelve la lista de productos ordenada por un campo y dirección determinados.
     *
     * @param campo el campo por el cual ordenar (nombre, precio o stock)
     * @param orden la dirección del ordenamiento (asc o desc)
     * @return lista ordenada de productos
     */
    @GetMapping("/ordenados")
    public ResponseEntity<List<ProductoResponse>> listarOrdenados(
            @RequestParam String campo,
            @RequestParam(required = false, defaultValue = "asc") String orden) {
        return ResponseEntity.ok(productoService.listarOrdenados(campo, orden));
    }
}
