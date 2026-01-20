package com.projeto.listacompras.exception;

/**
 * Exceção personalizada lançada quando há um conflito com os dados
 * 
 * Exemplos de conflito:
 * - Tentar criar item com nome que já existe
 * - Tentar atualizar item com nome que já existe em outro item
 * 
 * Deve retornar HTTP 409 (Conflict)
 * 
 * Uso:
 * throw new ItemConflictException("Já existe um item com o nome: Arroz");
 */
public class ItemConflictException extends RuntimeException {
    
    /**
     * Construtor com mensagem
     * @param message Mensagem descritiva do conflito
     */
    public ItemConflictException(String message) {
        super(message);
    }

    /**
     * Construtor com mensagem e causa
     * @param message Mensagem descritiva do conflito
     * @param cause Causa original da exceção
     */
    public ItemConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}