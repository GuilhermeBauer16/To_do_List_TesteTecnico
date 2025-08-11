package com.ToDoListTesteTecnico.controller;


import com.ToDoListTesteTecnico.controller.contract.TaskControllerContract;
import com.ToDoListTesteTecnico.entity.values.TaskVO;
import com.ToDoListTesteTecnico.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController implements TaskControllerContract {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public ResponseEntity<TaskVO> createTask(TaskVO task) {

        TaskVO taskVO = taskService.createTask(task);
        return new ResponseEntity<>(taskVO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<TaskVO> updateTask(TaskVO task) {
        return null;
    }

    @Override
    public ResponseEntity<TaskVO> getTaskById(String id) {

        TaskVO taskById = taskService.getTaskById(id);
        return ResponseEntity.ok(taskById);
    }

    @Override
    public void deleteTaskById(String id) {

    }
}
