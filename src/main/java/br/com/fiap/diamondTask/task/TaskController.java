package br.com.fiap.diamondTask.task;

import br.com.fiap.diamondTask.task.dto.TaskRequest;
import br.com.fiap.diamondTask.task.dto.TaskUpdateRequest;
import br.com.fiap.diamondTask.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    public TaskController(TaskService taskService, UserRepository userRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public Page<Task> getTasks(@PageableDefault(size = 3) Pageable pageable) {
        return taskService.findAll(pageable);
    }

    @GetMapping("{id}")
    public ResponseEntity<Task> getTask(@PathVariable UUID id) {
        try{
            Task task = taskService.findById(id);
            return ResponseEntity.ok().body(task);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public Task addTask(@RequestBody TaskRequest taskRequest) {
        var email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var task = taskRequest.toModel(user);
        return taskService.create(task);
    }

    @PutMapping("{id}")
    public Task updateTask(@PathVariable UUID id, @RequestBody TaskUpdateRequest taskUpdateRequest) {
        var email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return taskService.update(id, taskUpdateRequest, user);
    }

    @DeleteMapping("{id}")
    public void deleteTask(@PathVariable UUID id) {
        taskService.delete(id);
    }
}
