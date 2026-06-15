package com.inventory.smart.dto;

import com.inventory.smart.model.TipoMovimiento;
import java.time.LocalDateTime;

/**
 * DTO para la respuesta de creación o consulta de un movimiento de inventario.
 *
 * @param id              el identificador del movimiento generado
 * @param productoId      el identificador del producto afectado
 * @param tipo            el tipo de movimiento (ENTRADA o SALIDA)
 * @param cantidad        la cantidad de unidades operadas
 * @param stockResultante la cantidad final en stock resultante de la operación
 * @param motivo          la justificación del movimiento
 * @param fecha           la fecha y hora de la transacción
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public record MovimientoResponse(
    Long id,
    Long productoId,
    TipoMovimiento tipo,
    int cantidad,
    int stockResultante,
    String motivo,
    LocalDateTime fecha
) {}
