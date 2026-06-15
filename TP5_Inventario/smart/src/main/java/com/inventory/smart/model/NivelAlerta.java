package com.inventory.smart.model;

/**
 * Representa los niveles de alerta de stock para un producto.
 *
 * <p>Los niveles se definen en base a la relación entre el stock actual
 * y los umbrales mínimo y crítico configurados.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public enum NivelAlerta {
    /**
     * El stock está por encima o igual al umbral mínimo.
     */
    NORMAL,

    /**
     * El stock está por debajo del umbral mínimo pero por encima o igual al crítico.
     */
    BAJO,

    /**
     * El stock está por debajo del umbral crítico.
     */
    CRITICO
}
