package br.com.fiap.diamondTask.user;

import br.com.fiap.diamondTask.user.dto.UserFormRequest;
import br.com.fiap.diamondTask.user.dto.UserProfileResponse;
import br.com.fiap.diamondTask.user.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserFormRequest userForm, UriComponentsBuilder uriBuilder) {
        var usuario = userService.create(userForm.toModel());
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(UserResponse.from(usuario));
    }

    @GetMapping("profile")
    public UserProfileResponse getUserProfile() {
        var email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        System.out.println(email);
        return userService.getProfile(email);
    }

    @PostMapping("/avatar")
    public void uploadAvatar(@RequestBody MultipartFile file){
        var email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        userService.updateAvatar(email, file);
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String email) {


        userService.delete(email);

    }
}
