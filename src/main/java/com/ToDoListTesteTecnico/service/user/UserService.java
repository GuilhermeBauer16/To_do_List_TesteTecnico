package com.ToDoListTesteTecnico.service.user;


import com.ToDoListTesteTecnico.entity.UserEntity;
import com.ToDoListTesteTecnico.entity.values.UserVO;
import com.ToDoListTesteTecnico.exception.UserNotFoundException;
import com.ToDoListTesteTecnico.exception.utils.FieldNotFoundException;
import com.ToDoListTesteTecnico.mapper.BuilderMapper;
import com.ToDoListTesteTecnico.repository.UserRepository;
import com.ToDoListTesteTecnico.request.UserUpdateRequest;
import com.ToDoListTesteTecnico.service.contract.UserServiceContract;
import com.ToDoListTesteTecnico.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceContract {

    private final UserRepository userRepository;

    private final String USER_NOT_FOUND_MESSAGE = "User was not found!, Please verify and try again.";

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserVO findUserByEmail(String email) {

        UserEntity userEntity = userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));
        return BuilderMapper.parseObject(new UserVO(), userEntity);
    }

    @Override
    public UserVO updateUser(UserUpdateRequest userUpdateRequest) {

        UserEntity userEntity = userRepository.findUserByEmail(userUpdateRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        UserVO userVO = BuilderMapper
                .parseObject(new UserVO(), userUpdateRequest);

        UserEntity updatedUser = ValidatorUtils.updateFieldIfNotNull(userEntity, userVO, USER_NOT_FOUND_MESSAGE, FieldNotFoundException.class);
        ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(updatedUser,USER_NOT_FOUND_MESSAGE, FieldNotFoundException.class);
        userRepository.save(updatedUser);

        return BuilderMapper.parseObject(new UserVO(), updatedUser);
    }
}
