package com.raizes.raizes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

   // Captura nossos erros de Regra de Negócio (ex: Sem estoque) e devolve o Erro 409
    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<PadraoError> handleRegraNegocioException(RegraNegocioException e, HttpServletRequest request) {
        
        PadraoError err = new PadraoError(
            "ESTOQUE_INSUFICIENTE_OU_CONFLITO", 
            e.getMessage(),                     
            request.getRequestURI()             
        );
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    // Captura erros gerais de envio de dados inválidos e devolve 400 Bad Request
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<PadraoError> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        
        PadraoError err = new PadraoError(
            "DADOS_INVALIDOS",
            e.getMessage(),
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
