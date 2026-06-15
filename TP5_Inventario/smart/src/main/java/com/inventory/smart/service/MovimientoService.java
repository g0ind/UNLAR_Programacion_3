package com.inventory.smart.service;

import java.util.List;

import com.inventory.smart.dto.MovimientoRequest;
import com.inventory.smart.dto.MovimientoResponse;

/**
 * Servicio encargado de la gestión de movimientos de stock en el inventario.
 *
 * <p>Procesa entradas y salidas de stock garantizando la atomicidad de las operaciones
 * y la trazabilidad de los movimientos.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public interface MovimientoService {

    /**
     * Registra un movimiento de stock (ENTRADA o SALIDA) de forma atómica.
     *
     * @param request los datos del movimiento a registrar
     * @return el movimiento registrado mapeado a DTO
     */
    MovimientoResponse registrarMovimiento(MovimientoRequest request);

    /**
     * Recupera el historial de movimientos de un producto específico.
     *
     * @param productoId el identificador único del producto
     * @return lista de movimientos del producto ordenados por fecha
     */
    List<MovimientoResponse> getHistorialPorProducto(Long productoId);
}
