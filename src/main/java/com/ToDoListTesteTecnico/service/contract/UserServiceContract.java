package com.ToDoListTesteTecnico.service.contract;


import com.ToDoListTesteTecnico.entity.values.UserVO;
import com.ToDoListTesteTecnico.response.UserResponse;

/**
 * Service interface for managing user registration within the application.
 *
 * <p>This service provides methods to create user based on specific
 * criteria.
 */

public interface UserServiceContract {

    /**
     * Registers a new user in the system.
     *
     * <p>This method processes the user registration details provided in the {@link UserVO} object
     * and creates a new user account. It returns a response containing information about the
     * newly created user.
     *
     * @param userVO The {@link UserVO} object containing the user's registration details,
     *               such as username, email, password, and any other relevant information.
     * @return {@link UserRegistrationResponse} object with details of the newly created user,
     * including a unique user ID and other registration-related information.
     * @throws UserNotFoundException          if the {@link UserVO} object was empty or null .
     * @throws FieldNotFound                  if there is an issue with the registration values, such as empty or null field.
     * @throws EmailAllReadyRegisterException if the email passed is all ready register into the database preventing duplicated values.
     * @see UserRegistrationResponse
     * @see UserVO
     */

    UserResponse createUser(UserVO userVO);

    /**
     * Finds a user by their email address.
     * <p>
     * This method retrieves a user from the data source based on the provided email. If no user is found with
     * the given email, a {@link UserNotFoundException} is thrown.
     * </p>
     *
     * @param email the email address of the user to be retrieved
     * @return a {@link UserVO} object containing the user's data
     * @throws UserNotFoundException if no user is found with the provided email address
     */
    UserVO findUserByEmail(String email);
}
