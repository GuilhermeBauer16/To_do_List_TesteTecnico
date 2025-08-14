package com.ToDoListTesteTecnico.factory;


import com.ToDoListTesteTecnico.Enum.UserProfile;
import com.ToDoListTesteTecnico.entity.UserEntity;

import java.util.UUID;

public class UserFactory {
    public UserFactory() {
    }

    public static UserEntity create(String name, String email, String password, UserProfile userProfile) {
        return new UserEntity(UUID.randomUUID().toString(), name, email, password, userProfile);
    }
}
