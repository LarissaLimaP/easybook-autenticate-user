package com.puc.easybookautenticateuser.controller;

import com.puc.easybookautenticateuser.infra.security.DadosTokenValidacao;
import com.puc.easybookautenticateuser.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validar")
public class ValidacaoTokenController {

    @Value("${api.security.token.secret}")
    private String secret;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public Boolean TokenValido(@RequestBody DadosTokenValidacao dados) {
        System.out.println(dados.token().trim());
        return tokenService.validarToken(dados.token().trim());
    }
}
