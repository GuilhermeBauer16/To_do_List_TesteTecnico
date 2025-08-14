package com.ToDoListTesteTecnico.controller;


import com.ToDoListTesteTecnico.controller.contract.SubtaskControllerContract;
import com.ToDoListTesteTecnico.request.UpdateStatusRequest;
import com.ToDoListTesteTecnico.response.SubtaskResponse;
import com.ToDoListTesteTecnico.service.SubtaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subtask")
public class SubTaskController implements SubtaskControllerContract {

    private final SubtaskService subtaskService;

    @Autowired
    public SubTaskController(SubtaskService subtaskService) {
        this.subtaskService = subtaskService;
    }

    @Override
    @PatchMapping("updateStatus/{id}")
    public ResponseEntity<SubtaskResponse> updateSubTaskStatus(@PathVariable("id") String id, @RequestBody UpdateStatusRequest updateStatusRequest) {

        SubtaskResponse subtaskResponse = subtaskService.updateSubTaskStatus(id, updateStatusRequest);
        return ResponseEntity.ok(subtaskResponse);
    }


    @Override
    @GetMapping("/findSubtask/{id}")
    public ResponseEntity<SubtaskResponse> findSubTaskById(@PathVariable("id") String id) {
        SubtaskResponse subtaskResponse = subtaskService.findSubTaskById(id);
        return ResponseEntity.ok(subtaskResponse);
    }

    @Override
    @DeleteMapping("/deleteSubtask/{id}")
    public ResponseEntity<Void> deleteSubTask(@PathVariable("id") String id) {
        subtaskService.deleteSubTask(id);
        return ResponseEntity.noContent().build();
    }
}
