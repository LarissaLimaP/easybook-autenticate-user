package com.puc.easybookautenticateuser.controller;

import com.puc.easybookautenticateuser.format.UserDto;
import com.puc.easybookautenticateuser.model.User;
import com.puc.easybookautenticateuser.repository.UserRepository;
import com.puc.easybookautenticateuser.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UsersController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UsersController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/cadastro")
    @Transactional
    public ResponseEntity<String> cadastroUser(@RequestBody UserDto userDto) {

        //System.out.println(userDto.getDeletado());
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

    @GetMapping("/all")
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
