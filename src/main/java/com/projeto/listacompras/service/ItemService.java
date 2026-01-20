package com.projeto.listacompras.service;

import com.projeto.listacompras.dto.ItemDTO;
import com.projeto.listacompras.exception.ItemConflictException;
import com.projeto.listacompras.exception.ItemNotFoundException;
import com.projeto.listacompras.model.Item;
import com.projeto.listacompras.repository.ItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Serviço de negócio para gerenciar itens da lista de compras
 * 
 * Responsabilidades:
 * - Implementar lógica de negócio CRUD
 * - Validar dados antes de persistir no banco
 * - Lançar exceções apropriadas para casos de erro
 * - Converter entre DTO e Entity conforme necessário
 * 
 * @Service: Anotação que marca essa classe como um componente de serviço do Spring
 */
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * Construtor com injeção de dependência
     * O Spring injeta automaticamente uma instância de ItemRepository
     * 
     * @param itemRepository Repositório para acesso aos dados
     */
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * Criar um novo item na lista de compras
     * 
     * Validações realizadas:
     * 1. Verifica se já existe item com o mesmo nome
     * 2. Se existir, lança ItemConflictException
     * 
     * @param itemDTO Dados do item a ser criado (já validado pelo @Valid do controller)
     * @return Item criado (com ID gerado automaticamente)
     * @throws ItemConflictException se já existir item com esse nome
     */
    public Item criar(ItemDTO itemDTO) {
        // Validar se já existe item com o mesmo nome
        Optional<Item> itemExistente = itemRepository.findByNome(itemDTO.getNome());
        
        if (itemExistente.isPresent()) {
            throw new ItemConflictException(
                "Já existe um item com o nome: " + itemDTO.getNome()
            );
        }

        // Criar novo item e preencher dados do DTO
        Item item = new Item();
        item.setNome(itemDTO.getNome());
        item.setDescricao(itemDTO.getDescricao());
        item.setQuantidade(itemDTO.getQuantidade());

        // Salvar no banco e retornar
        return itemRepository.save(item);
    }

    /**
     * Obter todos os itens da lista de compras
     * 
     * @return Lista contendo todos os itens (vazia se nenhum existir)
     */
    public List<Item> obterTodos() {
        return itemRepository.findAll();
    }

    /**
     * Obter um item específico pelo ID
     * 
     * @param id ID do item a buscar
     * @return Item encontrado
     * @throws ItemNotFoundException se o item não existir
     */
    public Item obterPorId(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> 
                    new ItemNotFoundException("Item não encontrado com ID: " + id)
                );
    }

    /**
     * Editar um item existente
     * 
     * Validações realizadas:
     * 1. Verifica se o item existe (via obterPorId)
     * 2. Verifica se existe outro item com o mesmo nome
     * 3. Se existir outro com mesmo nome, lança ItemConflictException
     * 
     * @param id ID do item a editar
     * @param itemDTO Novos dados do item
     * @return Item atualizado
     * @throws ItemNotFoundException se o item não existir
     * @throws ItemConflictException se houver outro item com mesmo nome
     */
    public Item editar(Long id, ItemDTO itemDTO) {
        // Busca o item existente (se não existir, lança exceção)
        Item item = obterPorId(id);

        // Validar se existe OUTRO item com o mesmo nome
        Optional<Item> itemComMesmoNome = itemRepository.findByNome(itemDTO.getNome());
        
        if (itemComMesmoNome.isPresent() && !itemComMesmoNome.get().getId().equals(id)) {
            throw new ItemConflictException(
                "Já existe outro item com o nome: " + itemDTO.getNome()
            );
        }

        // Atualizar dados do item
        item.setNome(itemDTO.getNome());
        item.setDescricao(itemDTO.getDescricao());
        item.setQuantidade(itemDTO.getQuantidade());

        // Salvar no banco e retornar
        return itemRepository.save(item);
    }

    /**
     * Deletar um item da lista de compras
     * 
     * @param id ID do item a deletar
     * @throws ItemNotFoundException se o item não existir
     */
    public void deletar(Long id) {
        Item item = obterPorId(id);
        itemRepository.delete(item);
    }
}