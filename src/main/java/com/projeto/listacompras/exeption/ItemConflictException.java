package com.projeto.listacompras.exception;

/**
 * Exceção lançada quando tenta-se criar/editar um item com nome que já existe.
 * Deve retornar HTTP 409 (Conflict)
 */
public class ItemConflictException extends RuntimeException {
    public ItemConflictException(String mensagem) {
        super(mensagem);
    }
}