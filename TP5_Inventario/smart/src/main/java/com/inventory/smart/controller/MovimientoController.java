package com.inventory.smart.controller;

import com.inventory.smart.dto.MovimientoRequest;
import com.inventory.smart.dto.MovimientoResponse;
import com.inventory.smart.service.MovimientoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para registrar y consultar movimientos de stock en el almacén.
 *
 * <p>Permite registrar entradas y salidas de stock e inspeccionar el historial
 * por cada producto.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

<<<<<<< HEAD
}
=======
    private final MovimientoService movimientoService;

    /**
     * Inyección por constructor del servicio de movimientos.
     *
     * @param movimientoService el servicio de movimientos de stock
     */
    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    /**
     * Registra una transacción de entrada o salida de stock.
     *
     * @param request datos del movimiento a registrar
     * @return el movimiento registrado y estado HTTP 201 Created
     */
    @PostMapping
    public ResponseEntity<MovimientoResponse> registrar(@Valid @RequestBody MovimientoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoService.registrarMovimiento(request));
    }

    /**
     * Consulta el historial de movimientos de stock asociados a un producto específico.
     *
     * @param id identificador del producto
     * @return lista de movimientos del producto y estado HTTP 200 OK
     */
    @GetMapping("/producto/{id}")
    public ResponseEntity<List<MovimientoResponse>> obtenerHistorialPorProducto(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoService.getHistorialPorProducto(id));
    }
}
>>>>>>> 3bafdfd5b101e0efba743dc15016211a11568aa6
