package com.projeto.listacompras.repository;

import com.projeto.listacompras.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositório para acesso aos dados da entidade Item
 * 
 * Estende JpaRepository<Item, Long> para herdar automaticamente:
 * - save(Item): salvar novo item
 * - findById(Long): buscar por ID
 * - findAll(): obter todos os itens
 * - delete(Item): deletar um item
 * - deleteById(Long): deletar por ID
 * - e muitos outros...
 * 
 * Métodos customizados podem ser adicionados conforme necessário
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    /**
     * Buscar um item pelo nome
     * 
     * O Spring Data JPA gera automaticamente a query SQL:
     * SELECT * FROM itens WHERE nome = ?
     * 
     * @param nome Nome do item a buscar
     * @return Optional contendo o item se existir, ou vazio caso contrário
     * 
     * Uso:
     * Optional<Item> item = repository.findByNome("Arroz");
     */
    Optional<Item> findByNome(String nome);
}