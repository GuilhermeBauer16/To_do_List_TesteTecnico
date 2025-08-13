package com.ToDoListTesteTecnico.controller;


import com.ToDoListTesteTecnico.Enum.Priority;
import com.ToDoListTesteTecnico.Enum.Status;
import com.ToDoListTesteTecnico.controller.contract.TaskControllerContract;
import com.ToDoListTesteTecnico.entity.values.SubtaskVO;
import com.ToDoListTesteTecnico.entity.values.TaskVO;
import com.ToDoListTesteTecnico.service.SubtaskService;
import com.ToDoListTesteTecnico.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/task")
public class TaskController implements TaskControllerContract {

    private final TaskService taskService;
    private final SubtaskService subtaskService;

    @Autowired
    public TaskController(TaskService taskService, SubtaskService subtaskService) {
        this.taskService = taskService;
        this.subtaskService = subtaskService;
    }

    @Override
    @PostMapping
    public ResponseEntity<TaskVO> createTask(@RequestBody TaskVO task) {

        TaskVO taskVO = taskService.createTask(task);
        return new ResponseEntity<>(taskVO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<TaskVO> updateTask(TaskVO task) {
        return null;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TaskVO> getTaskById(@PathVariable("id") String id) {

        TaskVO taskById = taskService.getTaskById(id);
        return ResponseEntity.ok(taskById);
    }

    @Override
    @GetMapping("/status")
    public ResponseEntity<Page<TaskVO>> findAllTasksByStatus(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dueDate,
            Pageable pageable) {

        Page<TaskVO> allTasksByStatus = taskService.findAllTasksByStatus(status, priority, dueDate, pageable);
        return ResponseEntity.ok(allTasksByStatus);
    }


    @Override
    public void deleteTaskById(String id) {

    }

    @Override
    @PostMapping("/{taskId}/subtask")
    public ResponseEntity<TaskVO> addSubTaskToTask(@PathVariable("taskId") String taskId, @RequestBody SubtaskVO subtaskVO) {
        TaskVO taskVO = subtaskService.addSubTaskToTask(taskId, subtaskVO);
        return new ResponseEntity<>(taskVO, HttpStatus.CREATED);
    }
}
