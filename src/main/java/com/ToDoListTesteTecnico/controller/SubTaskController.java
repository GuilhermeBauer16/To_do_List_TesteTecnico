package com.ToDoListTesteTecnico.controller;


import com.ToDoListTesteTecnico.controller.contract.SubtaskControllerContract;
import com.ToDoListTesteTecnico.entity.values.SubtaskVO;
import com.ToDoListTesteTecnico.entity.values.TaskVO;
import com.ToDoListTesteTecnico.request.SubTaskRequest;
import com.ToDoListTesteTecnico.request.SubTaskUpdateRequest;
import com.ToDoListTesteTecnico.service.SubtaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<TaskVO> addSubTaskToTask(@PathVariable("taskId") String taskId, @RequestBody SubtaskVO subtaskVO) {
        return null;
    }

    @Override
    public ResponseEntity<TaskVO> updateSubTask(SubTaskUpdateRequest subTaskUpdateRequest) {
        return null;
    }
}
