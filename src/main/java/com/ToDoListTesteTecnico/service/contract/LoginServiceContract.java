package com.ToDoListTesteTecnico.service.contract;


import com.ToDoListTesteTecnico.request.LoginRequest;
import com.ToDoListTesteTecnico.response.LoginResponse;


public interface LoginServiceContract {


    LoginResponse login(LoginRequest loginRequest);
}
