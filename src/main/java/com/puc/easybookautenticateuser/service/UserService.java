package com.puc.easybookautenticateuser.service;

import com.puc.easybookautenticateuser.model.User;
import com.puc.easybookautenticateuser.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User cadastrarUsuario(User user) {
        try {
            if (userRepository.existsByUsername(user.getUsername())) {
                throw new IllegalArgumentException("Username já está em uso.");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (IllegalArgumentException | ResponseStatusException e) {
            log.error("Erro ao cadastrar usuário: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }
}