package com.ToDoListTesteTecnico.service.contract;

import com.ToDoListTesteTecnico.entity.values.TaskVO;

public interface TaskServiceContract {

    TaskVO createTask(TaskVO task);

    TaskVO updateTask(TaskVO task);

    TaskVO getTaskById(String id);

    void deleteTaskById(String id);
}
