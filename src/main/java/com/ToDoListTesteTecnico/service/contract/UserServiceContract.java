package com.ToDoListTesteTecnico.service.contract;


import com.ToDoListTesteTecnico.entity.values.UserVO;
import com.ToDoListTesteTecnico.response.UserResponse;


public interface UserServiceContract {


    UserResponse createUser(UserVO userVO);

    UserVO findUserByEmail(String email);
}
