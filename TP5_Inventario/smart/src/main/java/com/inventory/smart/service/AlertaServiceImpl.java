package com.inventory.smart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.inventory.smart.config.StockConfig;
import com.inventory.smart.dto.AlertaStockResponse;
import com.inventory.smart.model.NivelAlerta;
import com.inventory.smart.repository.ProductoRepository;

/**
 * Implementación de {@link AlertaService} utilizando el patrón Strategy para la evaluación.
 *
 * <p>Carga los umbrales configurados y delega la lógica de negocio al componente
 * de estrategia inyectado.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
@Service
public class AlertaServiceImpl implements AlertaService {

    private final ProductoRepository productoRepository;
    private final StockConfig stockConfig;
    private final IAlertaStrategy alertaStrategy;

    /**
     * Inyección por constructor de dependencias requeridas.
     *
     * @param productoRepository el repositorio de productos
     * @param stockConfig        la configuración de umbrales cargada de application.yml
     * @param alertaStrategy     la estrategia elegida para evaluar alertas
     */
    public AlertaServiceImpl(ProductoRepository productoRepository, StockConfig stockConfig, IAlertaStrategy alertaStrategy) {
        this.productoRepository = productoRepository;
        this.stockConfig = stockConfig;
        this.alertaStrategy = alertaStrategy;
    }

    /**
     * Obtiene una lista de todos los productos en estado de alerta (bajo o crítico).
     *
     * @return lista de productos con nivel de alerta BAJO o CRITICO
     */
    @Override
    public List<AlertaStockResponse> obtenerAlertasBajoStock() {
        int minimo = stockConfig.minimo();
        int critico = stockConfig.critico();

        return productoRepository.findAll().stream()
                .map(p -> {
                    NivelAlerta nivel = alertaStrategy.evaluar(p.getStock(), minimo, critico);
                    return new AlertaStockResponse(p.getId(), p.getNombre(), p.getStock(), nivel);
                })
                .filter(a -> a.nivelAlerta() != NivelAlerta.NORMAL)
                .toList();
    }
}
