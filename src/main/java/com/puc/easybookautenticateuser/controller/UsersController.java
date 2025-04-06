package com.puc.easybookautenticateuser.controller;

import com.puc.easybookautenticateuser.format.UserDto;
import com.puc.easybookautenticateuser.model.User;
import com.puc.easybookautenticateuser.repository.UserRepository;
import com.puc.easybookautenticateuser.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UsersController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsersController(UserService userService, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/")
    @Transactional
    public ResponseEntity<String> cadastroUser(@RequestBody UserDto userDto) {

        try {
            if (userDto.getSenha() == null || userDto.getSenha().isBlank()) {
                log.warn("Tentativa de cadastro com senha nula ou vazia.");
                return ResponseEntity.badRequest().body("A senha não pode ser nula ou vazia.");
            }

            if (userDto.getUsuario() == null || userDto.getUsuario().isBlank()) {
                log.warn("Tentativa de cadastro com username nulo ou vazio.");
                return ResponseEntity.badRequest().body("O username não pode ser nulo ou vazio.");
            }
            User user = new User();
            user.setUsuario(userDto.getUsuario());
            user.setSenha(userDto.getSenha());
            user.setDeletado(false);
            user.setTipo(userDto.getTipo());
            user.setNomeExibicao(userDto.getNomeExibicao());
            user.setFotoPerfil(userDto.getFotoPerfil());
            userService.cadastrarUsuario(user);
            return new ResponseEntity<>("Usuário cadastrado com sucesso!", HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            log.error("Erro ao cadastrar usuário: ", e);
            return ResponseEntity.badRequest().body("Parâmetros inválidos fornecidos.");
        } catch (Exception e) {
            log.error("Erro inesperado ao cadastrar usuário: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado ao cadastrar o usuário.");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<String> atualizarUsuario(@PathVariable Integer id, @RequestBody UserDto userDto) {
        try {
            User user = userRepository.findById(id).orElse(null);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
            }
            if (userDto.getUsuario() != null) {
                User existente = (User) userRepository.findByUsuario(userDto.getUsuario());
                if (existente != null && !existente.getId().equals(id)) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body("Já existe um usuário com esse nome de login.");
                }
                user.setUsuario(userDto.getUsuario());
            }
            if (userDto.getSenha() != null && !userDto.getSenha().isBlank()) {
                user.setSenha(passwordEncoder.encode(userDto.getSenha()));
            }
            if (userDto.getTipo() != null) {
                user.setTipo(userDto.getTipo());
            }
            if (userDto.getNomeExibicao() != null) {
                user.setNomeExibicao(userDto.getNomeExibicao());
            }
            if (userDto.getFotoPerfil() != null) {
                user.setFotoPerfil(userDto.getFotoPerfil());
            }
            userRepository.save(user);
            return ResponseEntity.ok("Usuário atualizado com sucesso!");
        } catch (Exception e) {
            log.error("Erro ao atualizar usuário: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar usuário.");
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deletarUsuario(@PathVariable Integer id) {
        try {
            User user = userRepository.findById(id).orElse(null);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
            }
            user.setDeletado(true);
            userRepository.save(user);
            return ResponseEntity.ok("Usuário deletado com sucesso!");
        } catch (Exception e) {
            log.error("Erro ao deletar usuário: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar usuário.");
        }
    }
}
