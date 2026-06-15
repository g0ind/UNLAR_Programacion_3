package com.inventory.smart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controlador de excepciones global para traducir excepciones del dominio a respuestas HTTP formadas.
 *
 * <p>Maneja excepciones de stock insuficiente, no encontrado, violación de reglas de negocio y errores de validación.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Constructor por defecto de la clase.
     */
    public GlobalExceptionHandler() {
    }

    /**
     * Maneja el caso cuando una entidad solicitada por ID no existe.
     *
     * @param ex la excepción atrapada
     * @return respuesta HTTP 404 Not Found con detalles
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", "Recurso no encontrado");
        body.put("mensaje", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    /**
     * Maneja el caso de stock insuficiente en operaciones de salida de mercadería.
     *
     * @param ex la excepción atrapada
     * @return respuesta HTTP 409 Conflict con estructura exacta solicitada
     */
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<Map<String, Object>> handleInsufficientStock(InsufficientStockException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", "Stock insuficiente");
        body.put("mensaje", ex.getMessage());
        body.put("productoId", ex.getProductoId());
        body.put("stockDisponible", ex.getStockDisponible());
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    /**
     * Maneja transacciones que violan reglas generales de negocio.
     *
     * @param ex la excepción atrapada
     * @return respuesta HTTP 409 Conflict con los detalles del error
     */
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessRule(BusinessRuleException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", "Violación de regla de negocio");
        body.put("mensaje", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    /**
     * Maneja fallos en las validaciones aplicadas a los DTOs de entrada.
     *
     * @param ex la excepción de validación capturada
     * @return respuesta HTTP 400 Bad Request con el detalle de las validaciones fallidas
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", "Datos de solicitud inválidos");
        
        Map<String, String> errores = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        e -> e.getDefaultMessage() != null ? e.getDefaultMessage() : "Campo inválido",
                        (msg1, msg2) -> msg1 + ", " + msg2
                ));
        
        body.put("errores", errores);
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * Capturador de excepciones generales de negocio o parámetros incorrectos.
     *
     * @param ex la excepción de argumento inválido o similar
     * @return respuesta HTTP 400 Bad Request
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", "Solicitud incorrecta");
        body.put("mensaje", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
