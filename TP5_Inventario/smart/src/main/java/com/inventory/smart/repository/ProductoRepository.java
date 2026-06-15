package com.inventory.smart.repository;

import com.inventory.smart.model.Producto;
import java.util.List;

/**
 * Contrato de repositorio para gestionar las operaciones del modelo {@link Producto}.
 *
 * <p>Extiende de {@link IGenericRepository} agregando firmas de métodos
 * de consulta específicas para el dominio de productos.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public interface ProductoRepository extends IGenericRepository<Producto, Long> {

    /**
     * Busca productos filtrados por una categoría específica.
     *
     * @param categoriaId el identificador de la categoría de búsqueda
     * @return lista de productos asociados a la categoría indicada
     */
    List<Producto> findByCategoria(Long categoriaId);

    /**
     * Realiza una búsqueda de productos que contengan un texto específico en su nombre.
     *
     * @param texto cadena de búsqueda
     * @return lista de productos coincidentes (insensible a mayúsculas/minúsculas)
     */
    List<Producto> buscarPorNombre(String texto);
}
