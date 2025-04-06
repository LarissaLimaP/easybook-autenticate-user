package com.puc.easybookautenticateuser.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {


    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().equals("/login")) {
            filterChain.doFilter(request, response);
            return;
        }if (request.getRequestURI().equals("/validar")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (request.getRequestURI().equals("/api/user/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String tokenJWT = recuperarToken(request);
        var subject = tokenService.getSubject(tokenJWT);



        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null) {
            throw new RuntimeException("Token JWT não enviado no cabeçalho 'authorization'.");
        }
        return authorizationHeader.replace("Bearer", "").trim();
    }
}
