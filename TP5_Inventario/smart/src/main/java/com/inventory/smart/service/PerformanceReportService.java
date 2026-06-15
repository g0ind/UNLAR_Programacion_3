package com.inventory.smart.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.inventory.smart.model.Categoria;
import com.inventory.smart.model.NivelAlerta;
import com.inventory.smart.model.Producto;

/**
 * Servicio encargado de generar el reporte de performance Big O empírico.
 *
 * <p>Mide los tiempos de ejecución en nanosegundos (ns) para conjuntos de datos
 * de 1k, 10k y 100k registros sobre las operaciones principales del sistema.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
@Service
public class PerformanceReportService {

    private final IAlertaStrategy alertaStrategy;

    /**
     * Inyección por constructor de la estrategia de alertas.
     *
     * @param alertaStrategy la estrategia para evaluar alertas
     */
    public PerformanceReportService(IAlertaStrategy alertaStrategy) {
        this.alertaStrategy = alertaStrategy;
    }

    /**
     * Genera el reporte completo en formato de mapa con las mediciones empíricas.
     *
     * @return un mapa estructurado con los resultados de performance
     */
    public Map<String, Object> generarReporte() {
        Map<String, Object> reporte = new LinkedHashMap<>();
        
        List<Integer> tamaños = List.of(1000, 10000, 100000);
        
        for (int tam : tamaños) {
            reporte.put(tam + "_registros", ejecutarPruebasParaTamaño(tam));
        }
        
        return reporte;
    }

    /**
     * Ejecuta las mediciones para un tamaño de datos específico.
     *
     * @param n cantidad de registros a simular
     * @return un mapa con los resultados de cada operación en nanosegundos (ns)
     */
    private Map<String, Object> ejecutarPruebasParaTamaño(int n) {
        Map<String, Object> resultados = new LinkedHashMap<>();
        
        // 1. Inicialización del dataset local en un ConcurrentHashMap
        ConcurrentHashMap<Long, Producto> dataStore = new ConcurrentHashMap<>(n);
        Categoria cat = new Categoria(1L, "Electrónicos");
        for (long i = 1; i <= n; i++) {
            Producto prod = new Producto(i, "Producto " + i, "Descripción del producto " + i, 100.0, 15, cat);
            dataStore.put(i, prod);
        }
        
        // 2. Medir POST /api/productos - O(1)
        long start = System.nanoTime();
        long nextId = n + 1;
        Producto nuevo = new Producto(nextId, "Producto Nuevo", "Desc", 10.0, 10, cat);
        dataStore.put(nextId, nuevo);
        long end = System.nanoTime();
        resultados.put("POST_productos_O_1", (end - start) + " ns");
        
        // Limpiamos la inserción para mantener el tamaño N
        dataStore.remove(nextId);

        // 3. Medir GET /api/productos/{id} - O(1)
        long idABuscar = (long) (n / 2);
        start = System.nanoTime();
        @SuppressWarnings("unused")
        Producto encontrado = dataStore.get(idABuscar);
        end = System.nanoTime();
        resultados.put("GET_producto_por_id_O_1", (end - start) + " ns");

        // 4. Medir GET /api/productos - O(n)
        start = System.nanoTime();
        @SuppressWarnings("unused")
        List<Producto> listaFiltrada = dataStore.values().stream()
                .filter(p -> p.getCategoria().getId().equals(1L))
                .filter(p -> p.getPrecio() >= 50.0)
                .filter(p -> p.getPrecio() <= 150.0)
                .filter(p -> p.getStock() > 0)
                .toList();
        end = System.nanoTime();
        resultados.put("GET_productos_filtrados_O_n", (end - start) + " ns");

        // 5. Medir GET /api/productos/buscar - O(n*m)
        String query = "Producto " + (n - 5);
        start = System.nanoTime();
        String lowerQuery = query.toLowerCase();
        @SuppressWarnings("unused")
        List<Producto> listaBusqueda = dataStore.values().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(lowerQuery))
                .toList();
        end = System.nanoTime();
        resultados.put("GET_productos_buscar_O_n_x_m", (end - start) + " ns");

        // 6. Medir GET /api/productos/ordenados - O(n log n)
        List<Producto> listaParaOrdenar = new ArrayList<>(dataStore.values());
        start = System.nanoTime();
        listaParaOrdenar.sort(Comparator.comparing(Producto::getNombre, String::compareToIgnoreCase));
        end = System.nanoTime();
        resultados.put("GET_productos_ordenados_O_n_log_n", (end - start) + " ns");

        // 7. Medir GET /api/alertas/stock-bajo - O(n)
        start = System.nanoTime();
        @SuppressWarnings("unused")
        List<Producto> alertas = dataStore.values().stream()
                .filter(p -> {
                    NivelAlerta nivel = alertaStrategy.evaluar(p.getStock(), 10, 3);
                    return nivel != NivelAlerta.NORMAL;
                })
                .toList();
        end = System.nanoTime();
        resultados.put("GET_alertas_stock_bajo_O_n", (end - start) + " ns");

        return resultados;
    }
}
