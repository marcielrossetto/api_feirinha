package com.projeto.listacompras.controller;

import com.projeto.listacompras.dto.ItemDTO;
import com.projeto.listacompras.model.Item;
import com.projeto.listacompras.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para gerenciar itens da lista de compras
 * 
 * Define todos os endpoints da API:
 * - POST /api/itens - Criar novo item
 * - GET /api/itens - Obter todos os itens
 * - GET /api/itens/{id} - Obter um item específico
 * - PUT /api/itens/{id} - Atualizar um item
 * - DELETE /api/itens/{id} - Deletar um item
 * 
 * Usa injeção de dependência via construtor (melhor prática)
 */
@RestController
@RequestMapping("/api/itens")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ItemController {

    private final ItemService itemService;

    /**
     * Construtor com injeção de dependência
     * @param itemService Serviço de negócio para itens
     */
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * POST /api/itens
     * Criar um novo item na lista de compras
     * 
     * @param itemDTO Dados do item a ser criado (validado automaticamente com @Valid)
     * @return ResponseEntity com o item criado (status 201 - CREATED)
     * 
     * Exemplo de requisição:
     * POST http://localhost:8080/api/itens
     * Content-Type: application/json
     * {
     *   "nome": "Arroz",
     *   "descricao": "Arroz integral 5kg",
     *   "quantidade": 1
     * }
     */
    @PostMapping
    public ResponseEntity<Item> criar(@Valid @RequestBody ItemDTO itemDTO) {
        Item itemCriado = itemService.criar(itemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemCriado);
    }

    /**
     * GET /api/itens
     * Obter todos os itens da lista de compras
     * 
     * @return ResponseEntity com lista de todos os itens (status 200 - OK)
     * 
     * Exemplo de requisição:
     * GET http://localhost:8080/api/itens
     */
    @GetMapping
    public ResponseEntity<List<Item>> obterTodos() {
        List<Item> itens = itemService.obterTodos();
        return ResponseEntity.status(HttpStatus.OK).body(itens);
    }

    /**
     * GET /api/itens/{id}
     * Obter um item específico pelo ID
     * 
     * @param id ID do item a ser recuperado
     * @return ResponseEntity com o item encontrado (status 200 - OK)
     * @throws ItemNotFoundException se o item não existir (status 404 - NOT FOUND)
     * 
     * Exemplo de requisição:
     * GET http://localhost:8080/api/itens/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<Item> obterPorId(@PathVariable Long id) {
        Item item = itemService.obterPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    /**
     * PUT /api/itens/{id}
     * Atualizar um item existente
     * 
     * @param id ID do item a ser atualizado
     * @param itemDTO Novos dados do item (validado automaticamente com @Valid)
     * @return ResponseEntity com o item atualizado (status 200 - OK)
     * @throws ItemNotFoundException se o item não existir (status 404 - NOT FOUND)
     * @throws ItemConflictException se houver conflito de nome (status 409 - CONFLICT)
     * 
     * Exemplo de requisição:
     * PUT http://localhost:8080/api/itens/1
     * Content-Type: application/json
     * {
     *   "nome": "Arroz Integral",
     *   "descricao": "Arroz integral 10kg",
     *   "quantidade": 2
     * }
     */
    @PutMapping("/{id}")
    public ResponseEntity<Item> editar(
            @PathVariable Long id,
            @Valid @RequestBody ItemDTO itemDTO) {
        Item itemAtualizado = itemService.editar(id, itemDTO);
        return ResponseEntity.status(HttpStatus.OK).body(itemAtualizado);
    }

    /**
     * DELETE /api/itens/{id}
     * Deletar um item da lista de compras
     * 
     * @param id ID do item a ser deletado
     * @return ResponseEntity vazio com status 204 (NO CONTENT) se deletado com sucesso
     * @throws ItemNotFoundException se o item não existir (status 404 - NOT FOUND)
     * 
     * Exemplo de requisição:
     * DELETE http://localhost:8080/api/itens/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        itemService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}