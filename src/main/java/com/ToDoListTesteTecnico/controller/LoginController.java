package com.ToDoListTesteTecnico.controller;


import com.ToDoListTesteTecnico.controller.contract.LoginControllerContract;
import com.ToDoListTesteTecnico.request.LoginRequest;
import com.ToDoListTesteTecnico.response.LoginResponse;
import com.ToDoListTesteTecnico.service.user.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController implements LoginControllerContract {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse login = loginService.login(request);
        return ResponseEntity.ok(login);
    }
}
