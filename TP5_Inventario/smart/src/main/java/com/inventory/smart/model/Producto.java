package com.inventory.smart.model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Representa un producto en el inventario del depósito inteligente.
 *
 * <p>Un producto posee un identificador único, nombre, descripción, precio,
 * stock (controlado de forma atómica) y pertenece a una categoría (composición).</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public class Producto {

    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private final AtomicInteger stock;
    private Categoria categoria;

    /**
     * Constructor por defecto para la inicialización y frameworks.
     *
     * <p>Inicializa el stock atómico en cero.</p>
     */
    public Producto() {
        this.stock = new AtomicInteger(0);
    }

    /**
     * Constructor con todos los campos.
     *
     * @param id           el identificador único del producto
     * @param nombre       el nombre descriptivo del producto
     * @param descripcion  la descripción detallada del producto
     * @param precio       el precio unitario del producto
     * @param stockInicial el stock inicial del producto (debe ser >= 0)
     * @param categoria    la categoría a la que pertenece el producto
     */
    public Producto(Long id, String nombre, String descripcion, double precio, int stockInicial, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = new AtomicInteger(stockInicial);
        this.categoria = categoria;
    }

    /**
     * Obtiene el identificador único del producto.
     *
     * @return el identificador del producto
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del producto.
     *
     * @param id el nuevo identificador del producto
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return el nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre el nuevo nombre del producto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción del producto.
     *
     * @return la descripción del producto
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del producto.
     *
     * @param descripcion la nueva descripción del producto
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el precio unitario del producto.
     *
     * @return el precio del producto
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio unitario del producto.
     *
     * @param precio el nuevo precio del producto
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene la cantidad de stock actual de manera thread-safe.
     *
     * @return la cantidad en stock
     */
    public int getStock() {
        return stock.get();
    }

    /**
     * Incrementa de forma atómica el stock del producto.
     *
     * @param cantidad la cantidad a adicionar
     * @return el stock resultante
     */
    public int incrementarStock(int cantidad) {
        return stock.addAndGet(cantidad);
    }

    /**
     * Decrementa de forma atómica el stock del producto.
     *
     * @param cantidad la cantidad a restar
     * @return el stock resultante
     */
    public int decrementarStock(int cantidad) {
        return stock.addAndGet(-cantidad);
    }

    /**
     * Obtiene la categoría a la que pertenece el producto.
     *
     * @return la categoría del producto
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Establece la categoría a la que pertenece el producto.
     *
     * @param categoria la nueva categoría del producto
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
