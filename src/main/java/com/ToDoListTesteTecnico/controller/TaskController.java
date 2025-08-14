package com.ToDoListTesteTecnico.controller;


import com.ToDoListTesteTecnico.Enum.Priority;
import com.ToDoListTesteTecnico.Enum.Status;
import com.ToDoListTesteTecnico.controller.contract.TaskControllerContract;
import com.ToDoListTesteTecnico.entity.values.SubtaskVO;
import com.ToDoListTesteTecnico.entity.values.TaskVO;
import com.ToDoListTesteTecnico.request.UpdateStatusRequest;
import com.ToDoListTesteTecnico.response.TaskResponse;
import com.ToDoListTesteTecnico.service.SubtaskService;
import com.ToDoListTesteTecnico.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskVO task) {

        TaskResponse taskResponse = taskService.createTask(task);
        return new ResponseEntity<>(taskResponse, HttpStatus.CREATED);
    }

    @Override
    @PatchMapping("/updateStatus/{id}")
    public ResponseEntity<TaskResponse> updateTaskStatus(@PathVariable("id") String id, @RequestBody UpdateStatusRequest updateStatusRequest) {
        TaskResponse taskResponse = taskService.updateTaskStatus(id, updateStatusRequest);
        return ResponseEntity.ok(taskResponse);
    }


    @Override
    @GetMapping("/findTask/{id}")
    public ResponseEntity<TaskResponse> findTaskById(@PathVariable("id") String id) {

        TaskResponse taskResponse = taskService.findTaskByIdWithResponse(id);
        return ResponseEntity.ok(taskResponse);
    }

    @Override
    @GetMapping("/findAll")
    public ResponseEntity<Page<TaskResponse>> findAllTasks(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dueDate,
            Pageable pageable) {

        Page<TaskResponse> allTasksByStatus = taskService.findAllTasks(status, priority, dueDate, pageable);
        return ResponseEntity.ok(allTasksByStatus);
    }

    @Override
    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable("id") String id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }


    @Override
    @PostMapping("/{taskId}/subtask")
    public ResponseEntity<TaskResponse> addSubTaskToTask(@PathVariable("taskId") String taskId, @RequestBody SubtaskVO subtaskVO) {
        TaskResponse taskResponse = subtaskService.addSubTaskToTask(taskId, subtaskVO);
        return new ResponseEntity<>(taskResponse, HttpStatus.CREATED);
    }
}
