package tech.buildrun.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Perfil {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @NotBlank public String telefone;
    @OneToOne(optional = false) @JoinColumn(unique = true)
    public Usuario usuario;
}
