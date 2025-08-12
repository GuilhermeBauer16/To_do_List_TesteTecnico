package com.ToDoListTesteTecnico.service.contract;

import com.ToDoListTesteTecnico.entity.values.TaskVO;
import com.ToDoListTesteTecnico.request.SubTaskRequest;
import com.ToDoListTesteTecnico.request.SubTaskUpdateRequest;

public interface SubTaskServiceContract {

    TaskVO addSubTask(SubTaskRequest subTaskRequest);

    TaskVO updateSubTask(SubTaskUpdateRequest subTaskUpdateRequest);
}
