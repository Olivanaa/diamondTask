package br.com.fiap.diamondTask.user.dto;

import br.com.fiap.diamondTask.user.Role;
import br.com.fiap.diamondTask.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserFormRequest(
        String nome,
        String email,
        String senha
) {
    public User toModel(){
        return User.builder()
                .id(UUID.randomUUID())
                .nome(nome)
                .email(email)
                .senha(senha)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .role(Role.ROLE_USER)
                .build();
    }
}
