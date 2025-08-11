package com.ToDoListTesteTecnico.controller.contract;

import com.ToDoListTesteTecnico.entity.values.TaskVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface TaskControllerContract {

    @PostMapping
    ResponseEntity<TaskVO> createTask(@RequestBody TaskVO task);

    @PutMapping
    ResponseEntity<TaskVO> updateTask(@RequestBody TaskVO task);

    @GetMapping("/{id}")
    ResponseEntity<TaskVO> getTaskById(@PathVariable("id") String id);

    void deleteTaskById(String id);
}
