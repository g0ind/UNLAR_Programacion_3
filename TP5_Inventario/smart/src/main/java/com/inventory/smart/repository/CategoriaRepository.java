package com.inventory.smart.repository;

import com.inventory.smart.model.Categoria;

/**
 * Contrato de repositorio para gestionar las operaciones del modelo {@link Categoria}.
 *
 * <p>Extiende de {@link IGenericRepository} para heredar los métodos CRUD básicos.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public interface CategoriaRepository extends IGenericRepository<Categoria, Long> {
}
