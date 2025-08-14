package com.ToDoListTesteTecnico.service.contract;

import com.ToDoListTesteTecnico.entity.SubtaskEntity;
import com.ToDoListTesteTecnico.entity.values.SubtaskVO;
import com.ToDoListTesteTecnico.entity.values.TaskVO;
import com.ToDoListTesteTecnico.request.UpdateStatusRequest;
import com.ToDoListTesteTecnico.response.SubtaskResponse;
import com.ToDoListTesteTecnico.response.TaskResponse;

public interface SubTaskServiceContract {

    SubtaskEntity createSubtask(SubtaskVO subtaskVO);

    TaskVO addSubTaskToTask(String taskId, SubtaskVO subtaskVO);

    SubtaskVO updateSubTaskStatus(String id, UpdateStatusRequest updateStatusRequest);

    SubtaskVO findSubTaskById(String id);

    void deleteSubTask(String id);
}
