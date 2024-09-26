package br.com.fiap.diamondTask.task;

import br.com.fiap.diamondTask.user.Role;
import br.com.fiap.diamondTask.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Data
@Table(name = "tarefas")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    UUID id = UUID.randomUUID();
    String titulo;
    String descricao;
    LocalDateTime dataEntrega;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    Prioridade prioridade;

    @ManyToOne
    User user;


}
