package br.com.fiap.diamondTask.task;

import br.com.fiap.diamondTask.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "tarefas")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    UUID id;
    String titulo;
    String descricao;
    String dataEntrega;
    String prioridade;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    @ManyToOne
    User user;
}
