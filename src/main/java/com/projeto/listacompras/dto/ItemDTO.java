package com.projeto.listacompras.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) para Item.
 * 
 * Usado para:
 * 1. Receber dados do cliente (requests)
 * 2. Validar os dados de entrada
 * 3. Enviar dados para o cliente (responses)
 * 
 * O DTO NÃO inclui o ID, pois é gerado pelo banco.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    /**
     * Nome do item.
     * 
     * @NotBlank: não pode ser null, vazio ou só espaços em branco
     * @NotNull: redundante com @NotBlank, mas deixa explícito
     * @Size(min = 1, max = 100): entre 1 e 100 caracteres
     */
    @NotBlank(message = "Nome não pode ser vazio")
    @NotNull(message = "Nome é obrigatório")
    @Size(min = 1, max = 100, message = "Nome deve ter entre 1 e 100 caracteres")
    private String name;

    /**
     * Quantidade do item.
     * 
     * @NotNull: não pode ser null
     * @Min(1): deve ser no mínimo 1
     * @Max(999999): limite máximo de quantidade
     */
    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser maior que 0")
    @Max(value = 999999, message = "Quantidade não pode ser maior que 999999")
    private Integer quantity;

}