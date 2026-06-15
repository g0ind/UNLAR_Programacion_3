package com.inventory.smart.model;

import java.time.LocalDateTime;

/**
 * Representa un movimiento de inventario (Entrada o Salida) en el depósito.
 *
 * <p>Mantiene un registro de la transacción, incluyendo la cantidad operada,
 * el stock final resultante, el motivo y la fecha/hora en la que ocurrió.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public class MovimientoInventario {

    private Long id;
    private Long productoId;
    private TipoMovimiento tipo;
    private int cantidad;
    private int stockResultante;
    private String motivo;
    private LocalDateTime fecha;

    /**
     * Constructor por defecto.
     */
    public MovimientoInventario() {
    }

    /**
     * Constructor con todos los campos del movimiento de inventario.
     *
     * @param id              el identificador del movimiento
     * @param productoId      el identificador del producto afectado
     * @param tipo            el tipo de movimiento (ENTRADA / SALIDA)
     * @param cantidad        la cantidad movilizada
     * @param stockResultante el stock restante luego del movimiento
     * @param motivo          la descripción o justificación del movimiento
     * @param fecha           la fecha y hora en la que se efectivizó el movimiento
     */
    public MovimientoInventario(Long id, Long productoId, TipoMovimiento tipo, int cantidad, int stockResultante, String motivo, LocalDateTime fecha) {
        this.id = id;
        this.productoId = productoId;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.stockResultante = stockResultante;
        this.motivo = motivo;
        this.fecha = fecha;
    }

    /**
     * Obtiene el identificador del movimiento.
     *
     * @return el identificador del movimiento
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del movimiento.
     *
     * @param id el nuevo identificador del movimiento
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el identificador del producto afectado.
     *
     * @return el identificador del producto
     */
    public Long getProductoId() {
        return productoId;
    }

    /**
     * Establece el identificador del producto afectado.
     *
     * @param productoId el nuevo identificador del producto
     */
    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    /**
     * Obtiene el tipo de movimiento.
     *
     * @return el tipo de movimiento
     */
    public TipoMovimiento getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de movimiento.
     *
     * @param tipo el nuevo tipo de movimiento
     */
    public void setTipo(TipoMovimiento tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la cantidad de unidades movilizadas.
     *
     * @return la cantidad movilizada
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad de unidades movilizadas.
     *
     * @param cantidad la nueva cantidad movilizada
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el stock resultante tras el movimiento.
     *
     * @return el stock resultante
     */
    public int getStockResultante() {
        return stockResultante;
    }

    /**
     * Establece el stock resultante tras el movimiento.
     *
     * @param stockResultante el nuevo stock resultante
     */
    public void setStockResultante(int stockResultante) {
        this.stockResultante = stockResultante;
    }

    /**
     * Obtiene el motivo del movimiento.
     *
     * @return el motivo
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * Establece el motivo del movimiento.
     *
     * @param motivo el nuevo motivo
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    /**
     * Obtiene la fecha y hora del registro.
     *
     * @return la fecha y hora
     */
    public LocalDateTime getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha y hora del registro.
     *
     * @param fecha la nueva fecha y hora
     */
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
