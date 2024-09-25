package br.com.fiap.diamondTask.auth;

import br.com.fiap.diamondTask.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public Token login(Credentials credentials) {
        var user = userRepository.findByEmail(credentials.email())
                .orElseThrow(()-> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(credentials.password(), user.getSenha())){
            throw new RuntimeException("Password incorreto");
        }

        return tokenService.generateToken(user);
    }
}
