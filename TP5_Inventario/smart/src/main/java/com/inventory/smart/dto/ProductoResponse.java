package com.inventory.smart.dto;

/**
 * DTO para la respuesta de consulta de un producto.
 *
 * @param id          el identificador único del producto
 * @param nombre      el nombre del producto
 * @param descripcion la descripción detallada del producto
 * @param precio      el precio unitario
 * @param stock       la cantidad actual en stock
 * @param categoria   los datos de la categoría asociada
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public record ProductoResponse(
    Long id,
    String nombre,
    String descripcion,
    double precio,
    int stock,
    CategoriaResponse categoria
) {}
