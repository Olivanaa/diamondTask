package br.com.fiap.diamondTask.user;

import br.com.fiap.diamondTask.user.dto.UserProfileResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }


    public UserProfileResponse getProfile(String email) {
        return userRepository.findByEmail(email)
                .map(UserProfileResponse::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

    }


    public User create(User user) {
        user.setSenha(passwordEncoder.encode(user.getSenha()));
        user.setAvatar("https://avatar.iran.liara.run/username?username=" + user.getNome());
        return userRepository.save(user);
    }

    public void updateAvatar(String email, MultipartFile file) {
        if (file.isEmpty()){
            throw new RuntimeException("Invalid file");
        }

        try(InputStream inputStream = file.getInputStream()) {
            Path destinationDir = Path.of("src/main/resources/static/avatars");
            Path destinationFile = destinationDir.resolve(System.currentTimeMillis() + file.getOriginalFilename())
                    .normalize()
                    .toAbsolutePath();
            Files.copy(inputStream, destinationFile);

            var user = userRepository.findByEmail(email).orElseThrow( () -> new UsernameNotFoundException("User not found"));
            var baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
            var avatarUrl = baseUrl + "/users/avatars/" + destinationFile.getFileName();
            user.setAvatar(avatarUrl);
            userRepository.save(user);

        } catch (Exception e) {
            System.out.println("Erro ao copiar arquivo. " + e.getCause());
        }
    }

    public void delete(String email) {
        var user = userRepository.findByEmail(email).orElseThrow( () -> new UsernameNotFoundException("User not found"));
        if (!userRepository.existsById(user.getId())) {
            throw new UsernameNotFoundException("User not found");
        }
        userRepository.deleteById(user.getId());
    }

}
