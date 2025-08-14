package com.ToDoListTesteTecnico.controller.contract;

import com.ToDoListTesteTecnico.entity.values.SubtaskVO;
import com.ToDoListTesteTecnico.request.UpdateStatusRequest;
import org.springframework.http.ResponseEntity;

public interface SubtaskControllerContract {


    ResponseEntity<SubtaskVO> updateSubTaskStatus(String id, UpdateStatusRequest updateStatusRequest);

    ResponseEntity<SubtaskVO> findSubTaskById(String id);

    ResponseEntity<Void> deleteSubTask(String id);
}
