package com.ToDoListTesteTecnico.controller.contract;

import com.ToDoListTesteTecnico.Enum.Priority;
import com.ToDoListTesteTecnico.Enum.Status;
import com.ToDoListTesteTecnico.entity.values.SubtaskVO;
import com.ToDoListTesteTecnico.entity.values.TaskVO;
import com.ToDoListTesteTecnico.request.UpdateStatusRequest;
import com.ToDoListTesteTecnico.response.TaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;


public interface TaskControllerContract {


    ResponseEntity<TaskVO> createTask(@RequestBody TaskVO task);

    ResponseEntity<TaskVO> updateTaskStatus(String id, UpdateStatusRequest updateStatusRequest);

    ResponseEntity<TaskVO> findTaskById(String id);

    ResponseEntity<Page<TaskVO>> findAllTasks(Status status, Priority priority,
                                              LocalDateTime dueDate, Pageable pageable);

    ResponseEntity<Void> deleteTaskById(String id);

    ResponseEntity<TaskVO> addSubTaskToTask(String taskId, SubtaskVO subtaskVO);
}
