package com.inventory.smart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para la solicitud de creación o actualización de una categoría.
 *
 * @param nombre el nombre descriptivo de la categoría (obligatorio, entre 2 y 50 caracteres)
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public record CategoriaRequest(
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre de la categoría debe tener entre 2 y 50 caracteres")
    String nombre
) {}
