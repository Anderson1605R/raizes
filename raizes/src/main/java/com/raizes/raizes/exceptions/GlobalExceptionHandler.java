package com.raizes.raizes.exceptions;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Captura nossos erros de Regra de Negócio (ex: Sem estoque) e devolve o Erro
    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<PadraoError> handleRegraNegocioException(RegraNegocioException e,
            HttpServletRequest request) {

        PadraoError err = new PadraoError(
                "ESTOQUE_INSUFICIENTE_OU_CONFLITO",
                e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    // Captura erros gerais de envio de dados inválidos e devolve 400 Bad Request
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<PadraoError> handleIllegalArgumentException(IllegalArgumentException e,
            HttpServletRequest request) {

        PadraoError err = new PadraoError(
                "DADOS_INVALIDOS",
                e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<Map<String, String>> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> Map.of("field", error.getField(), "issue", error.getDefaultMessage()))
                .collect(Collectors.toList());

        Map<String, Object> erroPadronizado = Map.of(
                "error", "ERRO_DE_VALIDACAO",
                "message", "Um ou mais campos obrigatórios estão ausentes ou inválidos",
                "details", details,
                "timestamp", Instant.now().toString(),
                "path", request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadronizado);
    }
}
