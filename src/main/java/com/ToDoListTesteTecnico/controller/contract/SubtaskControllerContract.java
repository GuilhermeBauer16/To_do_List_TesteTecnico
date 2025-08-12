package com.ToDoListTesteTecnico.controller.contract;

import com.ToDoListTesteTecnico.entity.values.TaskVO;
import com.ToDoListTesteTecnico.request.SubTaskRequest;
import com.ToDoListTesteTecnico.request.SubTaskUpdateRequest;
import org.springframework.http.ResponseEntity;

public interface SubtaskControllerContract {

    ResponseEntity<TaskVO> addSubTask(SubTaskRequest subTaskRequest);

    ResponseEntity<TaskVO> updateSubTask(SubTaskUpdateRequest subTaskUpdateRequest);
}
