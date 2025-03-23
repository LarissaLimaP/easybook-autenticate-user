package com.puc.easybookautenticateuser.usuario;

public record DadosDetalhamentoUsuario(
        Long id,
        String nomeExibicao,
        Tipo tipo,
        String fotoPerfil) {

    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNomeExibicao(),
                usuario.getTipo(),
                usuario.getFotoPerfil());
    }
}
