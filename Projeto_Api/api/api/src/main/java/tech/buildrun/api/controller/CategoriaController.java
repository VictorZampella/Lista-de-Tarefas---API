package tech.buildrun.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.buildrun.api.model.Categoria;
import tech.buildrun.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categorias", description = "Cadastro e consulta de categorias")
public class CategoriaController {
    private final CategoriaRepository repository;

    public CategoriaController(CategoriaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Listar categorias com paginação")
    public Page<Categoria> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria pelo ID")
    public ResponseEntity<Categoria> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastrar categoria")
    public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria item) {
        Categoria salvo = repository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar categoria")
    public ResponseEntity<Categoria> atualizar(@PathVariable Long id, @Valid @RequestBody Categoria item) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        item.id = id;
        return ResponseEntity.ok(repository.save(item));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir categoria")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar categorias por nome", description = "Exemplo: /categorias/buscar?nome=exemplo&page=0&size=10")
    public Page<Categoria> buscar(@RequestParam String nome, Pageable pageable) {
        return repository.findByNomeContainingIgnoreCase(nome, pageable);
    }
}
