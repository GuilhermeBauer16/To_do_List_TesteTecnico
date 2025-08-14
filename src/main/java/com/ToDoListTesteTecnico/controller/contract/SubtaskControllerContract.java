package com.ToDoListTesteTecnico.controller.contract;

import com.ToDoListTesteTecnico.entity.values.SubtaskVO;
import com.ToDoListTesteTecnico.request.UpdateStatusRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface SubtaskControllerContract {


    @Operation(summary = "Update subtask status",
            description = "Update subtask status and return the updated task",
            tags = "subtask")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = SubtaskVO.class))),

            @ApiResponse(responseCode = "404", description = "Will throw Field Not Found Exception or Sub Task Not Found Exception ",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    ResponseEntity<SubtaskVO> updateSubTaskStatus(String id, UpdateStatusRequest updateStatusRequest);


    @Operation(summary = "find subtask by Id",
            description = "Find subtask by id and returned the subtask",
            tags = "subtask")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = SubtaskVO.class))),

            @ApiResponse(responseCode = "404", description = "Will throw Field Not Found Exception or Sub Task Not Found Exception ",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    ResponseEntity<SubtaskVO> findSubTaskById(String id);


    @Operation(summary = "Delete subtask by Id",
            description = "Delete subtask by id and returned the subtask",
            tags = "subtask")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),

            @ApiResponse(responseCode = "404", description = "Will throw Sub Task Not Found Exception ",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    ResponseEntity<Void> deleteSubTask(String id);
}
