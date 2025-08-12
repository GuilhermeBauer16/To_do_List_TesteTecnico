package com.ToDoListTesteTecnico.service.contract;

import com.ToDoListTesteTecnico.Enum.Priority;
import com.ToDoListTesteTecnico.Enum.Status;
import com.ToDoListTesteTecnico.entity.values.TaskVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface TaskServiceContract {

    TaskVO createTask(TaskVO taskVO);

    TaskVO updateTask(TaskVO taskVO);

    TaskVO getTaskById(String id);

    Page<TaskVO> findAllTasksByStatus(Status status,Priority priority, LocalDateTime dueDate, Pageable pageable);


    void deleteTaskById(String id);
}
