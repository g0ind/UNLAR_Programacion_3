package com.inventory.smart.dto;

import com.inventory.smart.model.TipoMovimiento;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para la solicitud de creación de un movimiento de inventario.
 *
 * @param productoId el identificador único del producto afectado (obligatorio)
 * @param tipo       el tipo de movimiento (ENTRADA o SALIDA, obligatorio)
 * @param cantidad   la cantidad de unidades operadas (debe ser mayor a 0)
 * @param motivo     el motivo o justificación de la transacción (obligatorio)
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public record MovimientoRequest(
    @NotNull(message = "El identificador de producto es obligatorio")
    Long productoId,

    @NotNull(message = "El tipo de movimiento es obligatorio")
    TipoMovimiento tipo,

    @Min(value = 1, message = "La cantidad debe ser mayor o igual a 1")
    int cantidad,

    @NotBlank(message = "El motivo de movimiento es obligatorio")
    String motivo
) {}
