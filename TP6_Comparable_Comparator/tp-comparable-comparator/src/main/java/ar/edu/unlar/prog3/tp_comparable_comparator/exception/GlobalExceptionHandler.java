package ar.edu.unlar.prog3.tp_comparable_comparator.exception;

import ar.edu.unlar.prog3.tp_comparable_comparator.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CriterioOrdenInvalidoException.class)
    public ResponseEntity<ErrorResponse> manejarCriterioInvalido(CriterioOrdenInvalidoException ex) {
        ErrorResponse body = new ErrorResponse(
                "Criterio de ordenamiento no válido",
                ex.getCriterioRecibido(),
                ex.getCriteriosAceptados()
        );
        return ResponseEntity.badRequest().body(body);
    }
}
