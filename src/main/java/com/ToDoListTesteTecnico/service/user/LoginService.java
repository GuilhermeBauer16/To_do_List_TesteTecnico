package com.ToDoListTesteTecnico.service.user;

import com.ToDoListTesteTecnico.exception.user.UserCredentialsNotMatchedException;
import com.ToDoListTesteTecnico.request.LoginRequest;
import com.ToDoListTesteTecnico.response.LoginResponse;
import com.ToDoListTesteTecnico.service.contract.LoginServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginServiceContract {

    private static final String BAD_CREDENTIALS_MESSAGE = "An user with that email: %s was not matched";

    private final JwtTokenService jwtTokenService;
    private final JwtDetailsService jwtDetailsService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginService(JwtTokenService jwtTokenService, JwtDetailsService jwtDetailsService, AuthenticationManager authenticationManager) {
        this.jwtTokenService = jwtTokenService;
        this.jwtDetailsService = jwtDetailsService;
        this.authenticationManager = authenticationManager;

    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        this.authenticate(loginRequest);
        UserDetails userDetails = this.jwtDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String token = jwtTokenService.generateToken(userDetails);

        return new LoginResponse(loginRequest.getEmail(), token);
    }

    private void authenticate(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));


        } catch (BadCredentialsException e) {

            throw new UserCredentialsNotMatchedException(String.format(BAD_CREDENTIALS_MESSAGE, loginRequest.getEmail()));
        }
    }
}
