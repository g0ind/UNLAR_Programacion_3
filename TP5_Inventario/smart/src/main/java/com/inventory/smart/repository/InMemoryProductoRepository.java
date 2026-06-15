package com.inventory.smart.repository;

import com.inventory.smart.model.Producto;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Implementación en memoria del repositorio de productos.
 *
 * <p>Extiende de {@link GenericInMemoryRepository} para operaciones CRUD básicas
 * e implementa {@link ProductoRepository} para búsquedas personalizadas.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
@Repository
public class InMemoryProductoRepository
        extends GenericInMemoryRepository<Producto, Long>
        implements ProductoRepository {

    /**
     * Constructor por defecto del repositorio de productos en memoria.
     */
    public InMemoryProductoRepository() {
    }

    /**
     * Busca productos filtrados por una categoría específica.
     *
     * @param categoriaId el identificador de la categoría de búsqueda
     * @return lista de productos asociados a la categoría indicada
     */
    @Override
    public List<Producto> findByCategoria(Long categoriaId) {
        if (categoriaId == null) {
            return List.of();
        }
        return dataStore.values().stream()
                .filter(p -> p.getCategoria() != null && p.getCategoria().getId().equals(categoriaId))
                .toList();
    }

    /**
     * Realiza una búsqueda de productos que contengan un texto específico en su nombre.
     *
     * @param texto cadena de búsqueda
     * @return lista de productos coincidentes (insensible a mayúsculas/minúsculas)
     */
    @Override
    public List<Producto> buscarPorNombre(String texto) {
        if (texto == null || texto.isBlank()) {
            return List.of();
        }
        String lower = texto.toLowerCase();
        return dataStore.values().stream()
                .filter(p -> p.getNombre() != null && p.getNombre().toLowerCase().contains(lower))
                .toList();
    }
}
