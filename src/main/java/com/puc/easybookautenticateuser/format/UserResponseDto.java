package com.puc.easybookautenticateuser.format;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
    private String nomeExibicao;
    private Tipo tipo;
    private String fotoPerfil;
    private String usuario;
    private Boolean deletado;

    public UserResponseDto(String nomeExibicao, Tipo tipo, String fotoPerfil, String usuario, Boolean deletado) {
        this.nomeExibicao = nomeExibicao;
        this.tipo = tipo;
        this.fotoPerfil = fotoPerfil;
        this.usuario = usuario;
        this.deletado = deletado;
    }
}

