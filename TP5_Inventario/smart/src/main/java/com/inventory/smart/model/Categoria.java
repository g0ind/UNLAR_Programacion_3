package com.inventory.smart.model;

/**
 * Representa una categoría que agrupa productos en el inventario.
 *
 * <p>Cada categoría posee un identificador único y un nombre descriptivo.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public class Categoria {

    private Long id;
    private String nombre;

    /**
     * Constructor por defecto para la serialización y frameworks.
     */
    public Categoria() {
    }

    /**
     * Constructor con parámetros.
     *
     * @param id     el identificador de la categoría
     * @param nombre el nombre de la categoría
     */
    public Categoria(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Obtiene el identificador único de la categoría.
     *
     * @return el identificador de la categoría
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único de la categoría.
     *
     * @param id el nuevo identificador de la categoría
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre descriptivo de la categoría.
     *
     * @return el nombre de la categoría
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre descriptivo de la categoría.
     *
     * @param nombre el nuevo nombre de la categoría
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
