package br.com.fiap.diamondTask.task;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
