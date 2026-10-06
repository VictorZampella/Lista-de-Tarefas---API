package tech.buildrun.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Tarefa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @NotBlank public String titulo;
    public String descricao;
    public LocalDate prazo;
    @Enumerated(EnumType.STRING) public StatusTarefa status = StatusTarefa.PENDENTE;
    @ManyToOne(optional = false) public Usuario usuario;
    @ManyToOne public Categoria categoria;
    @ManyToMany public Set<Etiqueta> etiquetas = new HashSet<>();
    @OneToMany(mappedBy = "tarefa") @JsonIgnore public Set<Comentario> comentarios = new HashSet<>();
}
