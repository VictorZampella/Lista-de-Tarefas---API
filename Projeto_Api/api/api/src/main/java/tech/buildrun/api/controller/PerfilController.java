package tech.buildrun.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.buildrun.api.model.Perfil;
import tech.buildrun.api.repository.PerfilRepository;

@RestController
@RequestMapping("/perfis")
@Tag(name = "Perfis", description = "Cadastro e consulta de perfis")
public class PerfilController {
    private final PerfilRepository repository;

    public PerfilController(PerfilRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Listar perfis com paginação")
    public Page<Perfil> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar perfil pelo ID")
    public ResponseEntity<Perfil> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastrar perfil")
    public ResponseEntity<Perfil> criar(@Valid @RequestBody Perfil item) {
        Perfil salvo = repository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar perfil")
    public ResponseEntity<Perfil> atualizar(@PathVariable Long id, @Valid @RequestBody Perfil item) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        item.id = id;
        return ResponseEntity.ok(repository.save(item));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir perfil")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar perfis por telefone", description = "Exemplo: /perfis/buscar?telefone=exemplo&page=0&size=10")
    public Page<Perfil> buscar(@RequestParam String telefone, Pageable pageable) {
        return repository.findByTelefoneContaining(telefone, pageable);
    }
}
