package tech.buildrun.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.buildrun.api.model.Etiqueta;
import tech.buildrun.api.repository.EtiquetaRepository;

@RestController
@RequestMapping("/etiquetas")
@Tag(name = "Etiquetas", description = "Cadastro e consulta de etiquetas")
public class EtiquetaController {
    private final EtiquetaRepository repository;

    public EtiquetaController(EtiquetaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Listar etiquetas com paginação")
    public Page<Etiqueta> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar etiqueta pelo ID")
    public ResponseEntity<Etiqueta> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastrar etiqueta")
    public ResponseEntity<Etiqueta> criar(@Valid @RequestBody Etiqueta item) {
        Etiqueta salvo = repository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar etiqueta")
    public ResponseEntity<Etiqueta> atualizar(@PathVariable Long id, @Valid @RequestBody Etiqueta item) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        item.id = id;
        return ResponseEntity.ok(repository.save(item));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir etiqueta")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar etiquetas por nome", description = "Exemplo: /etiquetas/buscar?nome=exemplo&page=0&size=10")
    public Page<Etiqueta> buscar(@RequestParam String nome, Pageable pageable) {
        return repository.findByNomeContainingIgnoreCase(nome, pageable);
    }
}
