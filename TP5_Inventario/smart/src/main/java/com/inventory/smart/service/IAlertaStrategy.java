package com.inventory.smart.service;

import com.inventory.smart.model.NivelAlerta;

/**
 * Interfaz para definir la estrategia de evaluación de alertas de stock.
 *
 * <p>Permite intercambiar las reglas de negocio para determinar cuándo un producto
 * entra en estado de alerta bajo o crítico.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public interface IAlertaStrategy {

    /**
     * Evalúa el nivel de alerta correspondiente para un stock y umbrales dados.
     *
     * @param stock        la cantidad en stock actual
     * @param stockMinimo  el umbral de stock mínimo para alerta baja
     * @param stockCritico el umbral de stock crítico para alerta crítica
     * @return el {@link NivelAlerta} determinado por la estrategia
     */
    NivelAlerta evaluar(int stock, int stockMinimo, int stockCritico);
}
