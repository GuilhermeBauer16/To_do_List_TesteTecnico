package com.ToDoListTesteTecnico.service.contract;

import com.ToDoListTesteTecnico.entity.values.SubtaskVO;
import com.ToDoListTesteTecnico.entity.values.TaskVO;
import com.ToDoListTesteTecnico.request.SubTaskUpdateRequest;

public interface SubTaskServiceContract {

    SubtaskVO createSubtask(SubtaskVO subtaskVO);

    TaskVO addSubTaskToTask(String taskId, SubtaskVO subtaskVO);

    SubtaskVO updateSubTaskStatus(SubTaskUpdateRequest subTaskUpdateRequest);
}
