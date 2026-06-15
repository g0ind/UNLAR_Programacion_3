package com.inventory.smart.model;

/**
 * Representa los tipos de movimientos de inventario posibles.
 *
 * <p>Los movimientos pueden ser de entrada (incremento de stock) o
 * de salida (decremento de stock).</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public enum TipoMovimiento {
    /**
     * Entrada de mercadería al depósito (incrementa el stock).
     */
    ENTRADA,

    /**
     * Salida de mercadería del depósito (decrementa el stock).
     */
    SALIDA
}
