package tech.buildrun.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Comentario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @NotBlank public String texto;
    public LocalDateTime criadoEm = LocalDateTime.now();
    @ManyToOne(optional = false) public Tarefa tarefa;
    @ManyToOne(optional = false) public Usuario usuario;
}
