package com.inventory.smart.service;

import java.util.List;

<<<<<<< HEAD
}
=======
import com.inventory.smart.dto.CategoriaRequest;
import com.inventory.smart.dto.CategoriaResponse;

/**
 * Servicio encargado de la gestión de categorías de productos.
 *
 * <p>Proporciona operaciones CRUD y validaciones de integridad de negocio.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public interface CategoriaService {

    /**
     * Recupera todas las categorías registradas.
     *
     * @return lista de todas las categorías
     */
    List<CategoriaResponse> findAll();

    /**
     * Busca una categoría por su identificador único.
     *
     * @param id el identificador de la categoría
     * @return la categoría encontrada mapeada a DTO
     */
    CategoriaResponse findById(Long id);

    /**
     * Crea una nueva categoría.
     *
     * @param request los datos de la categoría a crear
     * @return la categoría creada mapeada a DTO
     */
    CategoriaResponse crear(CategoriaRequest request);

    /**
     * Actualiza una categoría existente.
     *
     * @param id      el identificador de la categoría a actualizar
     * @param request los nuevos datos de la categoría
     * @return la categoría actualizada mapeada a DTO
     */
    CategoriaResponse actualizar(Long id, CategoriaRequest request);

    /**
     * Elimina una categoría por su ID.
     *
     * <p>No se puede eliminar una categoría que posea productos asociados.</p>
     *
     * @param id el identificador de la categoría a eliminar
     */
    void deleteById(Long id);
}
>>>>>>> 3bafdfd5b101e0efba743dc15016211a11568aa6
