package com.inventory.smart.service;

import com.inventory.smart.dto.MovimientoRequest;
import com.inventory.smart.dto.MovimientoResponse;
import com.inventory.smart.exception.InsufficientStockException;
import com.inventory.smart.exception.ResourceNotFoundException;
import com.inventory.smart.model.MovimientoInventario;
import com.inventory.smart.model.Producto;
import com.inventory.smart.model.TipoMovimiento;
import com.inventory.smart.repository.MovimientoRepository;
import com.inventory.smart.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación de {@link MovimientoService} para registrar y consultar movimientos.
 *
 * <p>Asegura la atomicidad de la validación y actualización del stock de un producto
 * al procesar egresos e ingresos de inventario.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
@Service
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final ProductoRepository productoRepository;

    /**
     * Inyección por constructor de las dependencias requeridas.
     *
     * @param movimientoRepository el repositorio de movimientos
     * @param productoRepository   el repositorio de productos
     */
    public MovimientoServiceImpl(MovimientoRepository movimientoRepository, ProductoRepository productoRepository) {
        this.movimientoRepository = movimientoRepository;
        this.productoRepository = productoRepository;
    }

    /**
     * Registra un movimiento de stock (ENTRADA o SALIDA) de forma atómica.
     *
     * <p>Si es una salida, valida que exista stock suficiente. Bloquea de forma segura el producto
     * para evitar condiciones de carrera durante la verificación y actualización.</p>
     *
     * @param request los datos del movimiento a registrar
     * @return el DTO con el movimiento guardado
     * @throws ResourceNotFoundException  si el producto no existe
     * @throws InsufficientStockException si es una salida y supera el stock disponible
     */
    @Override
    public MovimientoResponse registrarMovimiento(MovimientoRequest request) {
        Producto producto = productoRepository.findById(request.productoId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + request.productoId()));

        int stockResultante;
        
        // Sincronizamos en la instancia de producto para hacer atómica la comprobación y actualización
        synchronized (producto) {
            if (request.tipo() == TipoMovimiento.SALIDA) {
                int stockActual = producto.getStock();
                if (stockActual < request.cantidad()) {
                    throw new InsufficientStockException(
                            "No se pueden retirar " + request.cantidad() + " unidades. Stock disponible: " + stockActual,
                            producto.getId(),
                            stockActual
                    );
                }
                stockResultante = producto.decrementarStock(request.cantidad());
            } else {
                stockResultante = producto.incrementarStock(request.cantidad());
            }
        }

        // Guardamos el producto actualizado
        productoRepository.save(producto);

        MovimientoInventario movimiento = new MovimientoInventario(
                null,
                producto.getId(),
                request.tipo(),
                request.cantidad(),
                stockResultante,
                request.motivo(),
                LocalDateTime.now()
        );

        MovimientoInventario guardado = movimientoRepository.save(movimiento);
        return mapToResponse(guardado);
    }

    /**
     * Recupera el historial de movimientos de un producto específico.
     *
     * @param productoId el identificador único del producto
     * @return lista de movimientos del producto
     * @throws ResourceNotFoundException si el producto no existe
     */
    @Override
    public List<MovimientoResponse> getHistorialPorProducto(Long productoId) {
        if (!productoRepository.existsById(productoId)) {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + productoId);
        }
        return movimientoRepository.findByProductoId(productoId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Mapea una entidad {@link MovimientoInventario} a su correspondiente DTO {@link MovimientoResponse}.
     *
     * @param m el movimiento a mapear
     * @return el DTO de respuesta
     */
    private MovimientoResponse mapToResponse(MovimientoInventario m) {
        return new MovimientoResponse(
                m.getId(),
                m.getProductoId(),
                m.getTipo(),
                m.getCantidad(),
                m.getStockResultante(),
                m.getMotivo(),
                m.getFecha()
        );
    }
}
