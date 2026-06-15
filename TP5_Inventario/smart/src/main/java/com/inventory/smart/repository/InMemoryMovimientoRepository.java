package com.inventory.smart.repository;

import com.inventory.smart.model.MovimientoInventario;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Implementación en memoria del repositorio de movimientos de inventario.
 *
 * <p>Extiende de {@link GenericInMemoryRepository} e implementa {@link MovimientoRepository}.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
@Repository
public class InMemoryMovimientoRepository
        extends GenericInMemoryRepository<MovimientoInventario, Long>
        implements MovimientoRepository {

    /**
     * Constructor por defecto del repositorio de movimientos de inventario en memoria.
     */
    public InMemoryMovimientoRepository() {
    }

    /**
     * Recupera el historial de movimientos asociados a un producto en particular.
     *
     * @param productoId el identificador del producto
     * @return la lista de movimientos del producto
     */
    @Override
    public List<MovimientoInventario> findByProductoId(Long productoId) {
        if (productoId == null) {
            return List.of();
