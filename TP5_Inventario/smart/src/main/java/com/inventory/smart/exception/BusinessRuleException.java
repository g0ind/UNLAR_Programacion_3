package com.inventory.smart.exception;

/**
 * Excepción lanzada cuando una operación viola una regla de negocio del sistema.
 *
 * <p>Esta excepción suele traducirse a un código de estado HTTP 409 Conflict.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public class BusinessRuleException extends RuntimeException {

    /**
     * Crea una nueva instancia con el mensaje descriptivo especificado.
     *
     * @param message el mensaje explicativo de la regla de negocio violada
     */
    public BusinessRuleException(String message) {
        super(message);
    }
}
