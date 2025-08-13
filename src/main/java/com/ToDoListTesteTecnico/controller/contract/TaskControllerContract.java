package com.ToDoListTesteTecnico.controller.contract;

import com.ToDoListTesteTecnico.Enum.Priority;
import com.ToDoListTesteTecnico.Enum.Status;
import com.ToDoListTesteTecnico.entity.values.SubtaskVO;
import com.ToDoListTesteTecnico.entity.values.TaskVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;


public interface TaskControllerContract {


    ResponseEntity<TaskVO> createTask(@RequestBody TaskVO task);

    @PutMapping
    ResponseEntity<TaskVO> updateTask(@RequestBody TaskVO task);


    ResponseEntity<TaskVO> getTaskById(String id);

    ResponseEntity<Page<TaskVO>> findAllTasksByStatus(Status status, Priority priority,
                                                      LocalDateTime dueDate, Pageable pageable);


    void deleteTaskById(String id);

    ResponseEntity<TaskVO> addSubTaskToTask(String taskId, SubtaskVO subtaskVO);
}
