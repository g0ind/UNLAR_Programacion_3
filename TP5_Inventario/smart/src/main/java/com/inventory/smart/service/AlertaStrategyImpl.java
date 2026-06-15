package com.inventory.smart.service;

import org.springframework.stereotype.Component;

import com.inventory.smart.model.NivelAlerta;

/**
 * Estrategia por defecto para evaluar alertas de stock.
 *
 * <p>Reglas de negocio:
 * Si {@code stock < stockCritico}, el nivel es {@link NivelAlerta#CRITICO}.
 * Si {@code stock < stockMinimo} (pero mayor o igual al crítico), el nivel es {@link NivelAlerta#BAJO}.
 * En cualquier otro caso, el nivel es {@link NivelAlerta#NORMAL}.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
@Component
public class AlertaStrategyImpl implements IAlertaStrategy {

    /**
     * Constructor por defecto de la estrategia de alerta.
     */
    public AlertaStrategyImpl() {
    }

    /**
     * Evalúa el nivel de alerta correspondiente para un stock y umbrales dados.
     *
     * @param stock        la cantidad en stock actual
     * @param stockMinimo  el umbral de stock mínimo para alerta baja
     * @param stockCritico el umbral de stock crítico para alerta crítica
     * @return el {@link NivelAlerta} determinado
     */
    @Override
    public NivelAlerta evaluar(int stock, int stockMinimo, int stockCritico) {
        if (stock < stockCritico) {
            return NivelAlerta.CRITICO;
        } else if (stock < stockMinimo) {
            return NivelAlerta.BAJO;
        } else {
            return NivelAlerta.NORMAL;
        }
    }
}
