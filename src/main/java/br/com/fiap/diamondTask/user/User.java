package br.com.fiap.diamondTask.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    UUID id;
    String email;
    String senha;
    String nome;
    String avatar;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    Role role;

}
