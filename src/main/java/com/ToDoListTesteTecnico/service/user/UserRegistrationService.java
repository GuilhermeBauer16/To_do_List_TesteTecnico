package com.ToDoListTesteTecnico.service.user;


import com.ToDoListTesteTecnico.Enum.UserProfile;
import com.ToDoListTesteTecnico.entity.UserEntity;
import com.ToDoListTesteTecnico.entity.values.UserVO;
import com.ToDoListTesteTecnico.exception.EmailAllReadyRegisterException;
import com.ToDoListTesteTecnico.exception.UserNotFoundException;
import com.ToDoListTesteTecnico.exception.utils.FieldNotFoundException;
import com.ToDoListTesteTecnico.factory.UserFactory;
import com.ToDoListTesteTecnico.mapper.BuilderMapper;
import com.ToDoListTesteTecnico.repository.UserRepository;
import com.ToDoListTesteTecnico.service.contract.UserRegistrationServiceContract;
import com.ToDoListTesteTecnico.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegistrationService implements UserRegistrationServiceContract {

    private static final String USER_NOT_FOUND_MESSAGE = "That User was not found";
    private static final String EMAIL_ALREADY_REGISTER_MESSAGE = "The email %s is already registered for another user!";


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Autowired
    public UserRegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    @Transactional
    public UserVO createUser(UserVO userVO) {

        ValidatorUtils.checkObjectIsNullOrThrowException(userVO, USER_NOT_FOUND_MESSAGE, UserNotFoundException.class);
        userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));

        checkIfEmailAlreadyRegistered(userVO.getEmail());

        UserEntity userFactory = UserFactory.create(userVO.getName(), userVO.getEmail(), userVO.getPassword(), UserProfile.ROLE_USER);
        ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(userFactory, USER_NOT_FOUND_MESSAGE, FieldNotFoundException.class);
        UserEntity userEntity = userRepository.save(userFactory);
        return BuilderMapper.parseObject(new UserVO(), userEntity);
    }

    private void checkIfEmailAlreadyRegistered(String email) {

        if (userRepository.findUserByEmail(email).isPresent()) {

            throw new EmailAllReadyRegisterException(String.format(EMAIL_ALREADY_REGISTER_MESSAGE, email));
        }
    }
}
