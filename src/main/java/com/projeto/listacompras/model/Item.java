package com.projeto.listacompras.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * Entidade Item - Representa um item da lista de compras no banco de dados
 * 
 * Mapeada para a tabela 'itens' via JPA/Hibernate
 * Usa Lombok para gerar automaticamente:
 * - Getters e Setters
 * - Construtores (sem argumentos e com todos os argumentos)
 * - toString(), equals() e hashCode()
 */
@Entity
@Table(name = "itens", indexes = {
    @Index(name = "idx_nome", columnList = "nome")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    /**
     * ID único do item - Chave primária
     * Gerado automaticamente pelo banco de dados (AUTO_INCREMENT)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do item - Campo obrigatório
     * 
     * Atributos:
     * - nullable = false: não pode ser null
     * - length = 100: máximo 100 caracteres
     * - unique = true: deve ser único no banco (não pode haver dois itens com mesmo nome)
     */
    @Column(nullable = false, length = 100, unique = true)
    private String nome;

    /**
     * Descrição opcional do item
     * 
     * Atributos:
     * - length = 255: máximo 255 caracteres
     * - nullable = true: pode ser null (opcional)
     */
    @Column(length = 255)
    private String descricao;

    /**
     * Quantidade do item a ser comprado
     * 
     * Atributos:
     * - nullable = false: não pode ser null (obrigatório)
     */
    @Column(nullable = false)
    private Integer quantidade;

    /**
     * Data e hora de criação do item
     * Preenchida automaticamente via @PrePersist
     * 
     * Atributos:
     * - updatable = false: não é modificada após criação
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Data e hora da última atualização do item
     * Atualizada automaticamente via @PreUpdate
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Hook JPA executado ANTES de persistir a entidade no banco
     * Preenche as datas de criação e atualização
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Hook JPA executado ANTES de atualizar a entidade no banco
     * Atualiza apenas a data de modificação
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * String de representação do objeto para debug
     * Útil para logging
     */
    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", quantidade=" + quantidade +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}