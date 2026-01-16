package com.projeto.listacompras.controller;

import com.projeto.listacompras.dto.ItemDTO;
import com.projeto.listacompras.model.Item;
import com.projeto.listacompras.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para gerenciar items da lista de compras.
 * 
 * @RestController combina @Controller + @ResponseBody
 * @RequestMapping define a rota base para todos os endpoints
 * 
 * ResponseEntity permite retornar dados + status HTTP customizado
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    /**
     * Service é injetado automaticamente pelo Spring.
     * O Controller delega a lógica de negócio para o Service.
     */
    @Autowired
    private ItemService itemService;

    /**
     * POST /items
     * 
     * Cria um novo item na lista de compras.
     * 
     * @Valid valida o ItemDTO antes de chegar no método
     *   Se houver erro de validação, Spring retorna 400 automaticamente
     * 
     * @param itemDTO dados do item a criar (recebido do body JSON)
     * @return 201 (Created) com o item criado no body
     * @throws ItemConflictException (409 Conflict) se nome já existe
     */
    @PostMapping
    public ResponseEntity<Item> criar(@Valid @RequestBody ItemDTO itemDTO) {
        Item itemCriado = itemService.criar(itemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemCriado);
    }

    /**
     * GET /items
     * 
     * Obtém todos os items da lista de compras.
     * 
     * @return 200 (OK) com a lista de items no body
     */
    @GetMapping
    public ResponseEntity<List<Item>> obterTodos() {
        List<Item> items = itemService.obterTodos();
        return ResponseEntity.ok(items);
    }

    /**
     * GET /items/:id
     * 
     * Obtém um item específico pelo ID.
     * 
     * @PathVariable extrai o ID da URL (ex: /items/123 -> id = 123)
     * 
     * @param id ID do item
     * @return 200 (OK) com o item no body
     * @throws ItemNotFoundException (404 Not Found) se ID não existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Item> obterPorId(@PathVariable Long id) {
        Item item = itemService.obterPorId(id);
        return ResponseEntity.ok(item);
    }

    /**
     * PUT /items/:id
     * 
     * Edita um item existente.
     * 
     * @param id ID do item a editar
     * @param itemDTO dados atualizados (recebido do body JSON)
     * @return 200 (OK) com o item atualizado no body
     * @throws ItemNotFoundException (404 Not Found) se ID não existe
     * @throws ItemConflictException (409 Conflict) se novo nome já existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<Item> editar(@PathVariable Long id, @Valid @RequestBody ItemDTO itemDTO) {
        Item itemEditado = itemService.editar(id, itemDTO);
        return ResponseEntity.ok(itemEditado);
    }

    /**
     * DELETE /items/:id
     * 
     * Deleta um item da lista de compras.
     * 
     * @param id ID do item a deletar
     * @return 204 (No Content) - sem body
     * @throws ItemNotFoundException (404 Not Found) se ID não existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        itemService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}