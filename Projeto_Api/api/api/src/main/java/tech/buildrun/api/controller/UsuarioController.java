package tech.buildrun.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.buildrun.api.model.Usuario;
import tech.buildrun.api.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Cadastro e consulta de usuários")
public class UsuarioController {
    private final UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Listar usuários com paginação")
    public Page<Usuario> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário pelo ID")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastrar usuário")
    public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario) {
        Usuario salvo = repository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuario.id = id;
        return ResponseEntity.ok(repository.save(usuario));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuário")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar usuários pelo nome", description = "Exemplo: /usuarios/buscar?nome=Ana&page=0&size=10")
    public Page<Usuario> buscarPorNome(@RequestParam String nome, Pageable pageable) {
        return repository.findByNomeContainingIgnoreCase(nome, pageable);
    }
}
