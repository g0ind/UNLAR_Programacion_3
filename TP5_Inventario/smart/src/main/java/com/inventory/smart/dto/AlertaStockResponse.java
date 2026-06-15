package com.inventory.smart.dto;

import com.inventory.smart.model.NivelAlerta;

/**
 * DTO para representar el estado de alerta de stock de un producto.
 *
 * @param productoId     el identificador único del producto
 * @param nombreProducto el nombre del producto
 * @param stockActual    la cantidad en stock actual
 * @param nivelAlerta    el nivel de alerta (NORMAL, BAJO o CRITICO)
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public record AlertaStockResponse(
    Long productoId,
    String nombreProducto,
    int stockActual,
    NivelAlerta nivelAlerta
) {}
