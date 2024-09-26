package br.com.fiap.diamondTask.task;


import br.com.fiap.diamondTask.task.dto.TaskUpdateRequest;
import br.com.fiap.diamondTask.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<Task> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public Task findById(UUID id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found with id " + id));
    }

    public void delete(UUID id) {
        taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found with id " + id));
        taskRepository.deleteById(id);
    }

    public Task update(UUID id, TaskUpdateRequest task, User user) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));
        Task updatedTask = Task.builder()
                .id(existingTask.getId())
                .titulo(task.titulo())
                .descricao(task.descricao())
                .dataEntrega(task.data())
                .createdAt(existingTask.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .user(user)
                .prioridade(definirPrioridade(task.data()))
                .build();
        return taskRepository.save(updatedTask);
    }

    public Task create(Task task) {
        task.setPrioridade(definirPrioridade(task.getDataEntrega()));
        return taskRepository.save(task);
    }

    private Prioridade definirPrioridade(LocalDateTime dataEntrega) {
        long daysUntilDeadline = ChronoUnit.DAYS.between(LocalDateTime.now(), dataEntrega);

        if (daysUntilDeadline <= 4) {
            return Prioridade.ALTA;
        } else if (daysUntilDeadline <= 10) {
            return Prioridade.MEDIA;
        } else {
            return Prioridade.BAIXA;
        }
    }


}
