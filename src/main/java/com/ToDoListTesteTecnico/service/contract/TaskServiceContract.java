package com.ToDoListTesteTecnico.service.contract;

import com.ToDoListTesteTecnico.Enum.Priority;
import com.ToDoListTesteTecnico.Enum.Status;
import com.ToDoListTesteTecnico.entity.values.SubtaskVO;
import com.ToDoListTesteTecnico.entity.values.TaskVO;
import com.ToDoListTesteTecnico.request.UpdateStatusRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface TaskServiceContract {

    TaskVO createTask(TaskVO taskVO);

    TaskVO updateTask(TaskVO taskVO);

    TaskVO updateTaskStatus(String id, UpdateStatusRequest updateStatusRequest);

    TaskVO findTaskById(String id);

    Page<TaskVO> findAllTasks(Status status, Priority priority, LocalDateTime dueDate, Pageable pageable);


    void deleteTaskById(String id);
}
