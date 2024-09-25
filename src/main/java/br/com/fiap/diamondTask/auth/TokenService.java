package br.com.fiap.diamondTask.auth;

import br.com.fiap.diamondTask.user.UserRepository;
import br.com.fiap.diamondTask.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class TokenService {

    private final UserRepository userRepository;
    private Algorithm algorithm;

    public TokenService(UserRepository userRepository, @Value("01918ad7-33dd-7db2-ba4d-af8ad3b0ddd2") String secret) {
        this.userRepository = userRepository;
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public Token generateToken(User user) {
        var expiresAt = LocalDateTime.now().plusHours(10).toInstant(ZoneOffset.ofHours(-3));

        var token = JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("nome", user.getNome())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole().name())
                .withExpiresAt(expiresAt)
                .sign(algorithm);
        return new Token(token, user.getNome(), user.getId().toString(), user.getRole().name(), user.getEmail());
    }

    public User getUser(String token) {
        var id = JWT.require(algorithm)
                .build()
                .verify(token)
                .getSubject();
        System.out.println("ID do usuário obtido do token: " + id);


        return userRepository.findById(UUID.fromString(id))
                .orElseThrow( () -> new UsernameNotFoundException("User not found"));
    }
}
