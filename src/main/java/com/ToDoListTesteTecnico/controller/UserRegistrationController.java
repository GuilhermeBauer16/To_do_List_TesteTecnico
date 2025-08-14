package com.ToDoListTesteTecnico.controller;

import com.ToDoListTesteTecnico.controller.contract.UserRegistrationControllerContract;
import com.ToDoListTesteTecnico.entity.values.UserVO;
import com.ToDoListTesteTecnico.response.UserResponse;
import com.ToDoListTesteTecnico.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserRegistrationController implements UserRegistrationControllerContract {


    private final UserService userRegistrationService;

    @Autowired

    public UserRegistrationController(UserService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> createUser(@RequestBody UserVO userVO) {

        UserResponse user = userRegistrationService.createUser(userVO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
