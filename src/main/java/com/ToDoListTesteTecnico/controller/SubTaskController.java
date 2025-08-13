package com.ToDoListTesteTecnico.controller;


import com.ToDoListTesteTecnico.controller.contract.SubtaskControllerContract;
import com.ToDoListTesteTecnico.entity.values.SubtaskVO;
import com.ToDoListTesteTecnico.entity.values.TaskVO;
import com.ToDoListTesteTecnico.request.UpdateStatusRequest;
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
    public ResponseEntity<SubtaskVO> updateSubTaskStatus(@PathVariable("id") String id, @RequestBody UpdateStatusRequest updateStatusRequest) {

        SubtaskVO subtaskVO = subtaskService.updateSubTaskStatus(id, updateStatusRequest);
        return ResponseEntity.ok(subtaskVO);
    }


    @Override
    @GetMapping("/findSubtask/{id}")
    public ResponseEntity<SubtaskVO> findSubTaskById(@PathVariable("id") String id) {
        SubtaskVO subtaskVO = subtaskService.findSubTaskById(id);
        return ResponseEntity.ok(subtaskVO);
    }

    @Override
    @DeleteMapping("/deleteSubtask/{id}")
    public ResponseEntity<Void> deleteSubTask(@PathVariable("id") String id) {
        subtaskService.deleteSubTask(id);
        return ResponseEntity.noContent().build();
    }
}
