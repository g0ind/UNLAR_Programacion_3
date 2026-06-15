package com.inventory.smart.service;

import java.util.List;

import com.inventory.smart.dto.AlertaStockResponse;

/**
 * Servicio encargado de la evaluación e identificación de productos en estado de alerta de stock.
 *
 * <p>Utiliza estrategias de alerta configurables para determinar si el stock actual
 * es normal, bajo o crítico.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public interface AlertaService {

    /**
     * Obtiene una lista de todos los productos en estado de alerta (bajo o crítico).
     *
     * @return lista de productos con stock inferior al umbral mínimo configurado
     */
    List<AlertaStockResponse> obtenerAlertasBajoStock();
}
