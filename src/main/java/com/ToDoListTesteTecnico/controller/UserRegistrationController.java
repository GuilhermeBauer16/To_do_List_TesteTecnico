package com.ToDoListTesteTecnico.controller;

import com.ToDoListTesteTecnico.controller.contract.UserRegistrationControllerContract;
import com.ToDoListTesteTecnico.entity.values.UserVO;
import com.ToDoListTesteTecnico.service.user.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserRegistrationController implements UserRegistrationControllerContract {


    private final UserRegistrationService userRegistrationService;

    @Autowired

    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @Override
    @PostMapping
    public ResponseEntity<UserVO> createUser(@RequestBody UserVO userVO) {

        UserVO user = userRegistrationService.createUser(userVO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
