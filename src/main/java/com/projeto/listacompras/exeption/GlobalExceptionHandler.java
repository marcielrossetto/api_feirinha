package com.projeto.listacompras.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Handler global de exceções.
 * 
 * @RestControllerAdvice intercepta exceções em toda a aplicação
 * e retorna respostas customizadas.
 * 
 * Benefícios:
 * 1. Controller fica mais limpo (sem try/catch)
 * 2. Tratamento consistente de erros
 * 3. Respostas padronizadas
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Tratamento para ItemNotFoundException (404).
     * 
     * @ExceptionHandler define qual exceção este método trata
     * 
     * @param ex exceção capturada
     * @return 404 (Not Found) com mensagem de erro
     */
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleItemNotFound(ItemNotFoundException ex) {
        Map<String, String> erro = new HashMap<>();
        erro.put("erro", ex.getMessage());
        erro.put("status", "404");
        
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(erro);
    }

    /**
     * Tratamento para ItemConflictException (409).
     * 
     * @param ex exceção capturada
     * @return 409 (Conflict) com mensagem de erro
     */
    @ExceptionHandler(ItemConflictException.class)
    public ResponseEntity<Map<String, String>> handleItemConflict(ItemConflictException ex) {
        Map<String, String> erro = new HashMap<>();
        erro.put("erro", ex.getMessage());
        erro.put("status", "409");
        
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(erro);
    }

    /**
     * Tratamento para erros de validação (400).
     * 
     * MethodArgumentNotValidException é lançado quando @Valid falha.
     * Por exemplo, quando @NotBlank falha ou @Min(1) falha.
     * 
     * @param ex exceção capturada
     * @return 400 (Bad Request) com detalhes dos erros de validação
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationError(MethodArgumentNotValidException ex) {
        Map<String, Object> erro = new HashMap<>();
        Map<String, String> campos = new HashMap<>();

        // Extrai os erros de cada campo
        ex.getBindingResult().getFieldErrors().forEach(field ->
            campos.put(field.getField(), field.getDefaultMessage())
        );

        erro.put("erro", "Erro de validação");
        erro.put("campos", campos);
        erro.put("status", "400");
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erro);
    }

    /**
     * Tratamento genérico para exceções não tratadas.
     * 
     * @param ex qualquer outra exceção
     * @return 500 (Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericError(Exception ex) {
        Map<String, String> erro = new HashMap<>();
        erro.put("erro", "Erro interno do servidor");
        erro.put("detalhes", ex.getMessage());
        erro.put("status", "500");
        
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(erro);
    }

}