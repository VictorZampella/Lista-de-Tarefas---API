package tech.buildrun.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.buildrun.api.model.Comentario;
import tech.buildrun.api.repository.ComentarioRepository;

@RestController
@RequestMapping("/comentarios")
@Tag(name = "Comentários", description = "Cadastro e consulta de comentários")
public class ComentarioController {
    private final ComentarioRepository repository;

    public ComentarioController(ComentarioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Listar comentários com paginação")
    public Page<Comentario> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar comentario pelo ID")
    public ResponseEntity<Comentario> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastrar comentario")
    public ResponseEntity<Comentario> criar(@Valid @RequestBody Comentario item) {
        Comentario salvo = repository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar comentario")
    public ResponseEntity<Comentario> atualizar(@PathVariable Long id, @Valid @RequestBody Comentario item) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        item.id = id;
        return ResponseEntity.ok(repository.save(item));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir comentario")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar comentários por texto", description = "Exemplo: /comentarios/buscar?texto=exemplo&page=0&size=10")
    public Page<Comentario> buscar(@RequestParam String texto, Pageable pageable) {
        return repository.findByTextoContainingIgnoreCase(texto, pageable);
    }
}
