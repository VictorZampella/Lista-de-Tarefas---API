package tech.buildrun.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.buildrun.api.model.Tarefa;
import tech.buildrun.api.model.StatusTarefa;
import tech.buildrun.api.repository.TarefaRepository;

@RestController
@RequestMapping("/tarefas")
@Tag(name = "Tarefas", description = "Cadastro e consulta de tarefas")
public class TarefaController {
    private final TarefaRepository repository;

    public TarefaController(TarefaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Listar tarefas com paginação")
    public Page<Tarefa> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tarefa pelo ID")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastrar tarefa")
    public ResponseEntity<Tarefa> criar(@Valid @RequestBody Tarefa item) {
        Tarefa salvo = repository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tarefa")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @Valid @RequestBody Tarefa item) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        item.id = id;
        return ResponseEntity.ok(repository.save(item));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir tarefa")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar tarefas por título", description = "Exemplo: /tarefas/buscar?titulo=exemplo&page=0&size=10")
    public Page<Tarefa> buscar(@RequestParam String titulo, Pageable pageable) {
        return repository.findByTituloContainingIgnoreCase(titulo, pageable);
    }

    @GetMapping("/status")
    @Operation(summary = "Filtrar tarefas pelo status", description = "Valores: PENDENTE, EM_ANDAMENTO ou CONCLUIDA")
    public Page<Tarefa> buscarPorStatus(@RequestParam StatusTarefa status, Pageable pageable) {
        return repository.findByStatus(status, pageable);
    }
}
