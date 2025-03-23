package com.puc.easybookautenticateuser.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.puc.easybookautenticateuser.usuario.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @GetMapping
    public List<DadosListagemUsuario> listar() {
        return repository.findAll().stream().map(DadosListagemUsuario::new).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity getUsuario(@PathVariable Long id) {
        var usuario = repository.getReferenceById(id);

        if(usuario.isDeletado()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }

    @Autowired
    private UsuarioRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroUsuario dados) {
        repository.save(new Usuario(dados));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoUsuario dados) {
        var usuario = repository.getReferenceById(dados.id());
        usuario.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id) {
        var usuario = repository.getReferenceById(id);
        usuario.deletar();
        return ResponseEntity.noContent().build();
    }
}
