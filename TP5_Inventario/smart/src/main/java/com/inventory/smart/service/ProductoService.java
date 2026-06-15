package com.inventory.smart.service;

import java.util.List;

import com.inventory.smart.dto.ProductoRequest;
import com.inventory.smart.dto.ProductoResponse;

/**
 * Servicio encargado de la gestión de productos del inventario.
 *
 * <p>Proporciona operaciones CRUD, filtrado de productos, búsqueda textual
 * y ordenamiento parametrizado de productos.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public interface ProductoService {

    /**
     * Lista todos los productos aplicando filtros opcionales.
     *
     * @param categoriaId identificador de la categoría a filtrar (opcional)
     * @param precioMin   precio mínimo a filtrar (opcional)
     * @param precioMax   precio máximo a filtrar (opcional)
     * @param enStock     si es verdadero, filtra productos que tengan stock > 0 (opcional)
     * @return lista de productos que coinciden con los criterios indicados
     */
    List<ProductoResponse> findAll(Long categoriaId, Double precioMin, Double precioMax, Boolean enStock);

    /**
     * Busca un producto por su identificador único.
     *
     * @param id identificador del producto
     * @return el producto encontrado mapeado a DTO
     */
    ProductoResponse findById(Long id);

    /**
     * Crea un nuevo producto en el catálogo.
     *
     * @param request los datos del producto a crear
     * @return el producto creado mapeado a DTO
     */
    ProductoResponse crear(ProductoRequest request);

    /**
     * Actualiza los datos de un producto existente.
     *
     * @param id      identificador del producto a actualizar
     * @param request los nuevos datos del producto
     * @return el producto actualizado mapeado a DTO
     */
    ProductoResponse actualizar(Long id, ProductoRequest request);

    /**
     * Elimina un producto del catálogo por su ID.
     *
     * @param id el identificador del producto a eliminar
     */
    void deleteById(Long id);

    /**
     * Realiza una búsqueda textual insensible a mayúsculas/minúsculas sobre el nombre de los productos.
     *
     * @param q texto de búsqueda (no puede estar vacío)
     * @return lista de productos que coinciden con el texto de búsqueda
     */
    List<ProductoResponse> buscarPorNombre(String q);

    /**
     * Devuelve los productos ordenados por un campo y dirección específicos.
     *
     * @param campo el campo de ordenación (nombre, precio o stock)
     * @param orden el sentido del ordenamiento (asc o desc)
     * @return lista de productos ordenados
     */
    List<ProductoResponse> listarOrdenados(String campo, String orden);
}
