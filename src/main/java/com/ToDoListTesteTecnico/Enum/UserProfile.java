package com.ToDoListTesteTecnico.Enum;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserProfile {

    @JsonProperty("user")
    ROLE_USER("USER");


    private final String profile;

    UserProfile(String userProfile) {
        this.profile = userProfile;
    }

    public String getProfile() {
        return profile;
    }

    private static final String UNKNOWN_USER_MESSAGE = "Unknown user profile: ";

    public static UserProfile fromString(String userProfile) {
        for (UserProfile profile : UserProfile.values()) {
            if (profile.profile.equalsIgnoreCase(userProfile)) {
                return profile;
            }
        }
        throw new IllegalArgumentException(UNKNOWN_USER_MESSAGE + userProfile);
    }
}
