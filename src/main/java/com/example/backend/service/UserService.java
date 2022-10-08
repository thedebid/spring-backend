package com.example.backend.service;

import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.LoginResponse;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public User register(User user) {
        User newUser = new User();
        newUser.setFullName(user.getFullName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());
        return userRepository.save(newUser);
    }

    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                    loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authenticate);

            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authenticate.getPrincipal();
            String originalInput = loginRequest.getUsername() + ":" + loginRequest.getPassword();
            String token = Base64.getEncoder().encodeToString(originalInput.getBytes());
            return new LoginResponse(token, loginRequest.getUsername(), user.getAuthorities().toString());
        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }
}
