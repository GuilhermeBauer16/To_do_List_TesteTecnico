package com.ToDoListTesteTecnico.controller.contract;

import com.ToDoListTesteTecnico.Enum.Priority;
import com.ToDoListTesteTecnico.Enum.Status;
import com.ToDoListTesteTecnico.entity.values.TaskVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


public interface TaskControllerContract {


    ResponseEntity<TaskVO> createTask(@RequestBody TaskVO task);

    @PutMapping
    ResponseEntity<TaskVO> updateTask(@RequestBody TaskVO task);


    ResponseEntity<TaskVO> getTaskById( String id);

    Page<TaskVO> findAllTasks(Pageable pageable);


    ResponseEntity<Page<TaskVO>> findAllTasksByStatus( Status status,Priority priority, LocalDateTime dueDate, Pageable pageable);

    ResponseEntity<Page<TaskVO>> findAllTasksByPriority(Priority priority, Pageable pageable);

    ResponseEntity<Page<TaskVO>> findAllTasksByDueDate(LocalDateTime dueDate, Pageable pageable);

    void deleteTaskById(String id);
}
