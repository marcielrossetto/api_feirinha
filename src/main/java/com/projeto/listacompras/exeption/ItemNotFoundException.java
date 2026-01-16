package com.projeto.listacompras.exception;

/**
 * Exceção lançada quando um item não é encontrado no banco de dados.
 * Deve retornar HTTP 404 (Not Found)
 */
public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String mensagem) {
        super(mensagem);
    }
}