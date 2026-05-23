package com.pontoeletronico.backend.security;

import com.pontoeletronico.backend.model.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET =
            "MINHA_CHAVE_SECRETA_SUPER_FORTE_123456789";

    private final Key key =
            Keys.hmacShaKeyFor(SECRET.getBytes());

    public String gerarToken(Usuario usuario) {

        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("role", usuario.getRole().name())
                .claim("nome", usuario.getNome())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60 * 24
                        )
                )
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extrairEmail(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean tokenValido(String token) {

        try {

            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}