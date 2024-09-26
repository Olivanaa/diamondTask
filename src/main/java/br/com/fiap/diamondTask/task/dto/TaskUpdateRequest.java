package br.com.fiap.diamondTask.task.dto;

import br.com.fiap.diamondTask.task.Task;
import br.com.fiap.diamondTask.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskUpdateRequest(
        UUID id,
        String titulo,
        String descricao,
        LocalDateTime data
) {

}
