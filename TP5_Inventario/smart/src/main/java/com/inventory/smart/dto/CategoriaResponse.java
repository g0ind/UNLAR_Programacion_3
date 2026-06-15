package com.inventory.smart.dto;

/**
 * DTO para la respuesta de consulta de una categoría.
 *
 * @param id     el identificador único de la categoría
 * @param nombre el nombre descriptivo de la categoría
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public record CategoriaResponse(
    Long id,
    String nombre
) {}
