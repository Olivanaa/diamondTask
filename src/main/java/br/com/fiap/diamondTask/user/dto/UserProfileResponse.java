package br.com.fiap.diamondTask.user.dto;

import br.com.fiap.diamondTask.user.User;

public record UserProfileResponse(
        String nome,
        String email,
        String avatar
) {
    public UserProfileResponse(User user) {
        this(user.getNome(), user.getEmail(), user.getAvatar());
    }
}
