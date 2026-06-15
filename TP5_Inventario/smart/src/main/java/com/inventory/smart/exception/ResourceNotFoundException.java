package com.inventory.smart.exception;

/**
 * Excepción lanzada cuando una entidad solicitada no existe en el sistema.
 *
 * <p>Esta excepción suele traducirse a un código de estado HTTP 404 Not Found.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Crea una nueva instancia con un mensaje descriptivo.
     *
     * @param message el mensaje que detalla el error
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
