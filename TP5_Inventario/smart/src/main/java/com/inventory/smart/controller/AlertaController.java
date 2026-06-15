package com.inventory.smart.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.smart.dto.AlertaStockResponse;
import com.inventory.smart.service.AlertaService;
import com.inventory.smart.service.PerformanceReportService;

/**
 * Controlador REST para consultas de alertas de inventario y tareas administrativas de rendimiento.
 *
 * <p>Expone endpoints para listar stock crítico/bajo y emitir reportes de performance Big O.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
@RestController
public class AlertaController {

<<<<<<< HEAD
}
=======
    private final AlertaService alertaService;
    private final PerformanceReportService performanceReportService;

    /**
     * Inyección por constructor de las dependencias requeridas.
     *
     * @param alertaService            el servicio de alertas de stock
     * @param performanceReportService el servicio de análisis de rendimiento
     */
    public AlertaController(AlertaService alertaService, PerformanceReportService performanceReportService) {
        this.alertaService = alertaService;
        this.performanceReportService = performanceReportService;
    }

    /**
     * Recupera todos los productos cuyo stock se encuentra por debajo del umbral mínimo.
     *
     * @return lista de alertas de stock y estado HTTP 200 OK
     */
    @GetMapping("/api/alertas/stock-bajo")
    public ResponseEntity<List<AlertaStockResponse>> obtenerAlertas() {
        return ResponseEntity.ok(alertaService.obtenerAlertasBajoStock());
    }

    /**
     * Endpoint administrativo que genera un informe JSON empírico de complejidad Big O.
     *
     * @return mapa JSON estructurado con las mediciones empíricas de performance
     */
    @GetMapping("/api/admin/performance-report")
    public ResponseEntity<Map<String, Object>> obtenerReportePerformance() {
        return ResponseEntity.ok(performanceReportService.generarReporte());
    }
}
>>>>>>> 3bafdfd5b101e0efba743dc15016211a11568aa6
