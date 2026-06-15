package com.inventory.smart.repository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz genérica para repositorios que define las operaciones CRUD básicas.
 *
 * @param <T>  tipo de la entidad gestionada
 * @param <ID> tipo del identificador de la entidad
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public interface IGenericRepository<T, ID> {

    /**
     * Recupera todos los elementos almacenados.
     *
     * @return una lista con todas las entidades
     */
    List<T> findAll();

    /**
     * Busca una entidad por su identificador único.
     *
     * @param id identificador de la entidad
     * @return un Optional conteniendo la entidad si existe, o vacío en caso contrario
     */
    Optional<T> findById(ID id);

    /**
     * Guarda la entidad especificada. Si no tiene ID asignado, se le asigna uno.
     * Si ya tiene ID, actualiza la entidad existente.
     *
     * @param entity la entidad a guardar
     * @return la entidad guardada con su ID asignado
     */
    T save(T entity);

    /**
     * Elimina una entidad por su identificador único.
     *
     * @param id el identificador de la entidad a eliminar
     */
    void deleteById(ID id);

    /**
     * Comprueba si existe una entidad con el identificador provisto.
     *
     * @param id el identificador a buscar
     * @return true si existe, false en caso contrario
     */
    boolean existsById(ID id);

    /**
     * Devuelve el total de elementos registrados.
     *
     * @return cantidad de elementos registrados
     */
    long count();
}
