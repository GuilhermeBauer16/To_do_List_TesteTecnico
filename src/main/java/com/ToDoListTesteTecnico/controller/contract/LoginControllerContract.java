package com.ToDoListTesteTecnico.controller.contract;


import com.ToDoListTesteTecnico.request.LoginRequest;
import com.ToDoListTesteTecnico.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


public interface LoginControllerContract {


    @Operation(summary = "Do a login",
            description = "Do a login and return an JWT token if was success",
            tags = "login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "BadCredentials Exception",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request);
}
