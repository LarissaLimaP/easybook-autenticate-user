package com.puc.easybookautenticateuser.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.puc.easybookautenticateuser.format.Tipo;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String nomeExibicao;
    @Column
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    @Column
    private String fotoPerfil;
    @Column
    private String usuario;
    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;
    @Column
    private Boolean deletado;

    public User(String usuario, String senha) {
    }
}
