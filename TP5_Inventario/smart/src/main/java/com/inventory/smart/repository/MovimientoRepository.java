package com.inventory.smart.repository;

import com.inventory.smart.model.MovimientoInventario;
import java.util.List;

/**
 * Contrato de repositorio para gestionar las operaciones de {@link MovimientoInventario}.
 *
 * <p>Extiende de {@link IGenericRepository} agregando consultas específicas para el historial.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public interface MovimientoRepository extends IGenericRepository<MovimientoInventario, Long> {

    /**
     * Recupera el historial de movimientos asociados a un producto en particular.
     *
     * @param productoId el identificador del producto
     * @return la lista de movimientos del producto
     */
    List<MovimientoInventario> findByProductoId(Long productoId);
}
