package tech.buildrun.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @NotBlank public String nome;
    @Email @NotBlank @Column(unique = true) public String email;
    @OneToMany(mappedBy = "usuario") @JsonIgnore public List<Tarefa> tarefas;
}
