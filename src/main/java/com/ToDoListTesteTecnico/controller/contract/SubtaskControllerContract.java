package com.ToDoListTesteTecnico.controller.contract;

import com.ToDoListTesteTecnico.entity.values.SubtaskVO;
import com.ToDoListTesteTecnico.request.UpdateStatusRequest;
import com.ToDoListTesteTecnico.response.SubtaskResponse;
import org.springframework.http.ResponseEntity;

public interface SubtaskControllerContract {


    ResponseEntity<SubtaskResponse> updateSubTaskStatus(String id, UpdateStatusRequest updateStatusRequest);

    ResponseEntity<SubtaskResponse> findSubTaskById(String id);

    ResponseEntity<Void> deleteSubTask(String id);
}
