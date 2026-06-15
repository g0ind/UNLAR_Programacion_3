package com.inventory.smart.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuración de umbrales de stock para el sistema de alertas.
 *
 * <p>Mapea las propiedades de configuración definidas con el prefijo {@code inventario.stock}
 * en el archivo {@code application.yml}.</p>
 *
 * @param minimo  el umbral mínimo general para alerta de stock bajo
 * @param critico el umbral crítico para alerta de stock crítico
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
@ConfigurationProperties(prefix = "inventario.stock")
public record StockConfig(int minimo, int critico) {
}
