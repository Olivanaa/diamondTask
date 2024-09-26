package br.com.fiap.diamondTask.task.dto;

import br.com.fiap.diamondTask.task.Task;
import br.com.fiap.diamondTask.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record TaskRequest(
        String titulo,
        String descricao,
        LocalDateTime data
) {
    public Task toModel(User user) {
        return Task.builder()
                .id(UUID.randomUUID())
                .titulo(titulo)
                .descricao(descricao)
                .dataEntrega(data)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
    }
}
