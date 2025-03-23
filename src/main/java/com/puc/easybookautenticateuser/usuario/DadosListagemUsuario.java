package com.puc.easybookautenticateuser.usuario;

public record DadosListagemUsuario(
        Long id,
        String nomeExibicao,
        String fotoPerfil) {

    public DadosListagemUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNomeExibicao(), usuario.getFotoPerfil());
    }
}
