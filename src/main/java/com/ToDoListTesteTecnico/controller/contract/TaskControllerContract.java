package com.ToDoListTesteTecnico.controller.contract;

import com.ToDoListTesteTecnico.Enum.Priority;
import com.ToDoListTesteTecnico.Enum.Status;
import com.ToDoListTesteTecnico.entity.values.SubtaskVO;
import com.ToDoListTesteTecnico.entity.values.TaskVO;
import com.ToDoListTesteTecnico.request.UpdateStatusRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

public interface TaskControllerContract {


    @Operation(summary = "Create task",
            description = "Create a task and returned the created task",
            tags = "task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = TaskVO.class))),
            @ApiResponse(responseCode = "400", description = "Will throw Invalid Task Exception or Invalid Task Status Exception",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Will throw Field Not Found Exception",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    ResponseEntity<TaskVO> createTask(@RequestBody TaskVO task);


    @Operation(summary = "Update task status",
            description = "Update task status and return the updated task",
            tags = "task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = TaskVO.class))),
            @ApiResponse(responseCode = "400", description = "Will throw SubTask Not Completed Exception",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Will throw Field Not Found Exception or Task Not Found Exception ",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    ResponseEntity<TaskVO> updateTaskStatus(String id, UpdateStatusRequest updateStatusRequest);


    @Operation(summary = "find task by Id",
            description = "Find task by id and returned the task",
            tags = "task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = TaskVO.class))),

            @ApiResponse(responseCode = "404", description = "Will throw Field Not Found Exception or Task Not Found Exception ",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    ResponseEntity<TaskVO> findTaskById(String id);


    @Operation(summary = "find all tasks",
            description = "Find all tasks associated with the user and return paginated",
            tags = "task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = TaskVO.class)))

    })
    ResponseEntity<Page<TaskVO>> findAllTasks(Status status, Priority priority,
                                              LocalDateTime dueDate, Pageable pageable);


    @Operation(summary = "delete task by Id",
            description = "Delete task by id and return no content",
            tags = "task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),

            @ApiResponse(responseCode = "404", description = "Will throw Task Not Found Exception ",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    ResponseEntity<Void> deleteTaskById(String id);

    @Operation(summary = "Add sub task to a task",
            description = "add sub task to a task and return the task with the subtask",
            tags = "task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = TaskVO.class))),
            @ApiResponse(responseCode = "400", description = "Will throw Invalid SubTask Exception",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Will throw Field Not Found Exception or Task Not Found Exception ",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    ResponseEntity<TaskVO> addSubTaskToTask(String taskId, SubtaskVO subtaskVO);
}
