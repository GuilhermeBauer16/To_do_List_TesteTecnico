package com.ToDoListTesteTecnico.controller.contract;

import com.ToDoListTesteTecnico.entity.values.UserVO;
import com.ToDoListTesteTecnico.response.UserResponse;
import org.springframework.http.ResponseEntity;

public interface UserRegistrationControllerContract {

    ResponseEntity<UserResponse> createUser(UserVO userVO);
}
