package com.inventory.smart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

/**
 * DTO para la solicitud de creación o actualización de un producto.
 *
 * @param nombre       el nombre del producto (obligatorio, entre 2 y 100 caracteres)
 * @param descripcion  la descripción del producto (opcional, máximo 500 caracteres)
 * @param precio       el precio unitario (obligatorio, debe ser mayor o igual a 0)
 * @param stockInicial la cantidad de stock inicial (obligatoria, debe ser mayor o igual a 0)
 * @param categoriaId  el identificador único de la categoría asociada (obligatorio)
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public record ProductoRequest(
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    String nombre,

    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    String descripcion,

    @PositiveOrZero(message = "El precio debe ser >= 0")
    double precio,

    @PositiveOrZero(message = "El stock inicial debe ser >= 0")
    int stockInicial,

    @NotNull(message = "La categoría es obligatoria")
    Long categoriaId
) {}
