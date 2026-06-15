package com.inventory.smart.exception;

/**
 * Excepción lanzada cuando se intenta realizar un egreso de stock que supera las unidades disponibles.
 *
 * <p>Esta excepción se asocia comúnmente con el código HTTP 409 Conflict.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public class InsufficientStockException extends RuntimeException {

    /**
     * El identificador del producto afectado por la insuficiencia de stock.
     */
    private final Long productoId;

    /**
     * La cantidad de unidades actualmente disponibles en stock.
     */
    private final int stockDisponible;

    /**
     * Crea una nueva instancia de la excepción de stock insuficiente.
     *
     * @param message         el mensaje detallado de error
     * @param productoId      el identificador del producto con stock insuficiente
     * @param stockDisponible la cantidad de unidades actualmente disponibles
     */
    public InsufficientStockException(String message, Long productoId, int stockDisponible) {
        super(message);
        this.productoId = productoId;
        this.stockDisponible = stockDisponible;
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
     * Obtiene la cantidad de stock disponible al momento de la falla.
     *
     * @return el stock disponible
     */
    public int getStockDisponible() {
        return stockDisponible;
    }
}
