package com.puc.easybookautenticateuser.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroUsuario(

        @NotBlank
        String nomeExibicao,

        @NotNull
        Tipo tipo,

        String fotoPerfil,

        @NotNull
        String usuario,

        @NotNull
        String senha) {

}
