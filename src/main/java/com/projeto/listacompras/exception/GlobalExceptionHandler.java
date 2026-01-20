package com.projeto.listacompras.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Manipulador centralizado de exceções (Exception Handler)
 * 
 * Responsabilidades:
 * - Capturar exceções lançadas em controladores
 * - Transformar em respostas HTTP apropriadas
 * - Manter consistência nas respostas de erro
 * 
 * @RestControllerAdvice: Aplica a todos os controladores da aplicação
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Trata exceção ItemNotFoundException
     * Retorna HTTP 404 (NOT FOUND)
     * 
     * @param ex Exceção lançada
     * @return ResponseEntity com mapa de erro
     */
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleItemNotFound(ItemNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("erro", ex.getMessage());
        error.put("timestamp", LocalDateTime.now().format(formatter));
        error.put("mensagem", "Item não foi encontrado no banco de dados");
        
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    /**
     * Trata exceção ItemConflictException
     * Retorna HTTP 409 (CONFLICT)
     * 
     * @param ex Exceção lançada
     * @return ResponseEntity com mapa de erro
     */
    @ExceptionHandler(ItemConflictException.class)
    public ResponseEntity<Map<String, Object>> handleItemConflict(ItemConflictException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", HttpStatus.CONFLICT.value());
        error.put("erro", ex.getMessage());
        error.put("timestamp", LocalDateTime.now().format(formatter));
        error.put("mensagem", "Há um conflito com os dados fornecidos");
        
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    /**
     * Trata exceção MethodArgumentNotValidException
     * Lançada quando @Valid detecta erros de validação
     * Retorna HTTP 400 (BAD REQUEST)
     * 
     * @param ex Exceção lançada
     * @return ResponseEntity com mapa de erro contendo detalhes de validação
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException ex) {
        
        Map<String, Object> error = new HashMap<>();
        Map<String, String> fieldErrors = new HashMap<>();
        
        // Extrai todos os erros de validação de campo
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage())
        );
        
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("erro", "Falha na validação de dados");
        error.put("detalhes", fieldErrors);
        error.put("timestamp", LocalDateTime.now().format(formatter));
        error.put("mensagem", "Um ou mais campos contêm valores inválidos");
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    /**
     * Trata qualquer exceção genérica não capturada
     * Retorna HTTP 500 (INTERNAL SERVER ERROR)
     * 
     * @param ex Exceção lançada
     * @return ResponseEntity com mapa de erro genérico
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("erro", "Erro interno do servidor");
        error.put("detalhes", ex.getMessage());
        error.put("timestamp", LocalDateTime.now().format(formatter));
        error.put("mensagem", "Um erro inesperado ocorreu. Por favor, contate o administrador");
        
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}