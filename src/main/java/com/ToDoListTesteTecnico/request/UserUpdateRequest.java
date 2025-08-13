package com.ToDoListTesteTecnico.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


public class UserUpdateRequest {
    private String email;
    private String verifyCode;
    private boolean authenticated;
    private LocalDateTime codeExpiration;

    public UserUpdateRequest() {
    }

    public UserUpdateRequest(String email, String verifyCode, boolean authenticated, LocalDateTime codeExpiration) {
        this.email = email;
        this.verifyCode = verifyCode;
        this.authenticated = authenticated;
        this.codeExpiration = codeExpiration;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public LocalDateTime getCodeExpiration() {
        return codeExpiration;
    }

    public void setCodeExpiration(LocalDateTime codeExpiration) {
        this.codeExpiration = codeExpiration;
    }
}
