package com.projeto.listacompras.exception;

/**
 * Exceção personalizada lançada quando um item não é encontrado no banco de dados
 * 
 * Deve retornar HTTP 404 (Not Found)
 * 
 * Uso:
 * throw new ItemNotFoundException("Item com ID 5 não encontrado");
 */
public class ItemNotFoundException extends RuntimeException {
    
    /**
     * Construtor com mensagem
     * @param message Mensagem descritiva do erro
     */
    public ItemNotFoundException(String message) {
        super(message);
    }

    /**
     * Construtor com mensagem e causa
     * @param message Mensagem descritiva do erro
     * @param cause Causa original da exceção
     */
    public ItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}