package com.puc.easybookautenticateuser.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.puc.easybookautenticateuser.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String validarToken(String token) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT;
            JWTVerifier verifier = JWT.require(algoritmo)
                    .withIssuer("API eixo 6") // Verifica o emissor
                    .build();
            decodedJWT = verifier.verify(token); // Valida o token
            return decodedJWT.getClaims().toString(); // Token é legítimo e válido
        } catch (JWTVerificationException exception) {
            return "Token inválido ou expirado!"; // Token inválido ou expirado
        }
    }


    public String gerarToken(User usuario) {

        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API eixo 6")
                    .withSubject(usuario.getUsuario())
                    .withExpiresAt(dataExpiracao())
                    .withClaim("id", usuario.getId())
                    .withClaim("tipo", usuario.getTipo().toString())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("erro ao gerar token jwt", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT;
            JWTVerifier verifier = JWT.require(algoritmo)
                    .withIssuer("API eixo 6")
                    .build();

            decodedJWT = verifier.verify(tokenJWT);

            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
