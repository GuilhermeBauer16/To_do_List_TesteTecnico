package com.ToDoListTesteTecnico.service;

import com.ToDoListTesteTecnico.entity.TaskEntity;
import com.ToDoListTesteTecnico.entity.values.TaskVO;
import com.ToDoListTesteTecnico.exception.FieldNotFoundException;
import com.ToDoListTesteTecnico.mapper.BuilderMapper;
import com.ToDoListTesteTecnico.repository.TaskRepository;
import com.ToDoListTesteTecnico.request.SubTaskRequest;
import com.ToDoListTesteTecnico.request.SubTaskUpdateRequest;
import com.ToDoListTesteTecnico.service.contract.SubTaskServiceContract;
import com.ToDoListTesteTecnico.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubtaskService implements SubTaskServiceContract {

    private final TaskService taskService;
    private final TaskRepository taskRepository;

    private static final String TASK_NOT_FOUND_EXCEPTION_MESSAGE = "This Task was not found";

    @Autowired
    public SubtaskService(TaskService taskService, TaskRepository taskRepository) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskVO addSubTask(SubTaskRequest subTaskRequest) {

        if(subTaskRequest.getTaskId().equals(subTaskRequest.getSubTaskId())) {

            throw new RuntimeException("the two ids are equals, please verify and try again");
        }

        TaskVO taskById = taskService.getTaskById(subTaskRequest.getTaskId());
        TaskVO subtaskById = taskService.getTaskById(subTaskRequest.getSubTaskId());

        verifyIfHaveDuplicatedSubtask(subTaskRequest.getSubTaskId(),taskById);

        if(subtaskById.getSubTasks().size() > 0) {

            throw new RuntimeException("Is not posible add tasks to subtasks!!");
        }


        TaskEntity subTaskEntity = BuilderMapper.parseObject(new TaskEntity(), subtaskById);

        for(TaskEntity taskEntity : taskById.getSubTasks()) {

        }
        ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(subTaskEntity, TASK_NOT_FOUND_EXCEPTION_MESSAGE, FieldNotFoundException.class);
        taskById.getSubTasks().add(subTaskEntity);
        TaskEntity taskEntity = BuilderMapper.parseObject(new TaskEntity(), taskById);
        taskRepository.save(taskEntity);

        return  BuilderMapper.parseObject(new TaskVO(), taskEntity);
    }

    @Override
    public TaskVO updateSubTask(SubTaskUpdateRequest subTaskUpdateRequest) {
        return null;
    }

    private void verifyIfHaveDuplicatedSubtask(String subTaskId, TaskVO taskVO) {

        for (int i = 0; i < taskVO.getSubTasks().size(); i++) {

            if(subTaskId.equals(taskVO.getSubTasks().get(i).getId()))
                throw new RuntimeException("This subtask is already associated with this task!");

        }
    }
}
