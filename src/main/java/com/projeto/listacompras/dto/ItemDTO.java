package com.projeto.listacompras.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) para Item
 * 
 * Responsabilidades:
 * - Receber dados do cliente (requisições HTTP)
 * - Validar os dados de entrada com anotações Jakarta Validation
 * - Enviar dados para o cliente (respostas HTTP)
 * 
 * Nota: O ID não é incluído aqui pois é gerado automaticamente pelo banco
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    /**
     * Nome do item da lista de compras
     * 
     * Validações:
     * - @NotBlank: não pode ser null, vazio ou apenas espaços em branco
     * - @NotNull: redundante com @NotBlank, mas deixa explícito a obrigatoriedade
     * - @Size(min = 3, max = 100): entre 3 e 100 caracteres
     */
    @NotBlank(message = "O nome do item não pode estar em branco")
    @NotNull(message = "O nome do item é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    /**
     * Descrição opcional do item
     * 
     * Validações:
     * - @Size(max = 255): máximo 255 caracteres
     */
    @Size(max = 255, message = "A descrição não pode exceder 255 caracteres")
    private String descricao;

    /**
     * Quantidade do item a ser comprado
     * 
     * Validações:
     * - @NotNull: não pode ser null (obrigatório)
     * - @Min(1): deve ser no mínimo 1
     * - @Max(10000): não pode exceder 10.000
     */
    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade deve ser no mínimo 1")
    @Max(value = 10000, message = "A quantidade não pode exceder 10.000")
    private Integer quantidade;
}