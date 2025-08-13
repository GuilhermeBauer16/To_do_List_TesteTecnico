package com.ToDoListTesteTecnico.controller.contract;

import com.ToDoListTesteTecnico.entity.values.UserVO;
import org.springframework.http.ResponseEntity;

public interface UserRegistrationControllerContract {

    ResponseEntity<UserVO> createUser(UserVO userVO);
}
