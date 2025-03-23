package com.puc.easybookautenticateuser.usuario;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoUsuario(
        @NotNull
        Long id,

        String nomeExibicao,
        Tipo tipo,
        String fotoPerfil,
        String usuario,
        String senha
) {
}
