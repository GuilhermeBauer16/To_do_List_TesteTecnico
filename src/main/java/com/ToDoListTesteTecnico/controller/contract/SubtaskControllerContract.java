package com.ToDoListTesteTecnico.controller.contract;

import com.ToDoListTesteTecnico.entity.values.SubtaskVO;
import com.ToDoListTesteTecnico.entity.values.TaskVO;
import com.ToDoListTesteTecnico.request.SubTaskUpdateRequest;
import org.springframework.http.ResponseEntity;

public interface SubtaskControllerContract {

    ResponseEntity<TaskVO> addSubTaskToTask(String taskId, SubtaskVO subtaskVO);

    ResponseEntity<TaskVO> updateSubTask(SubTaskUpdateRequest subTaskUpdateRequest);
}
