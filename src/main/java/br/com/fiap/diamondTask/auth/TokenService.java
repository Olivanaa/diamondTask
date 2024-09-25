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

    public TokenService(UserRepository userRepository, @Value("${jwt.secret}") String secret) {
        this.userRepository = userRepository;
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public Token generateToken(User user) {
        var expiresAt = LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.ofHours(-3));

        var token = JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("nome", user.getNome())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole())
                .withExpiresAt(expiresAt)
                .sign(algorithm);
        return new Token(token, user.getNome(), user.getId().toString(), user.getRole(), user.getEmail());
    }

    public User getUser(String token) {
        var id = JWT.require(algorithm)
                .build()
                .verify(token)
                .getSubject();

        return userRepository.findById(Integer.valueOf(id))
                .orElseThrow( () -> new UsernameNotFoundException("User not found"));
    }
}
