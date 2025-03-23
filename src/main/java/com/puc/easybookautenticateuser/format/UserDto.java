package com.puc.easybookautenticateuser.format;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private String nomeExibicao;
    private Tipo tipo;
    private String fotoPerfil;
    private String usuario;
    private String senha;
    private Boolean deletado;

    @Override
    public String toString() {
        return "UserDto{" +
                "usuario='" + usuario + '\'' +
                ", senha='" + senha + '\'' +
                ", deletado='" + 0 + '\'' +
                ", nomeExibicao='" + nomeExibicao + '\'' +
                ", fotoPerfil='" + fotoPerfil + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
