package com.estate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.estate.controller.request.AuthenticationRequest;
import com.estate.controller.response.AuthenticationResponse;
import com.estate.controller.request.RegisterRequest;
import com.estate.exception.ResourceNotFoundException;
import com.estate.model.User;
import com.estate.repository.UserRepository;

import java.util.Date;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Date now = new Date();

        User user = new User(
            request.getEmail(),
            request.getName(),
            passwordEncoder.encode(request.getPassword()),
            now,
            now
        );

        userRepository.save(user);

        String jwt = jwtService.generateToken(user);

        return new AuthenticationResponse(jwt);
            
    }

    public AuthenticationResponse login(AuthenticationRequest request) {

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException(e.getMessage());
        }

        String jwt = jwtService.generateToken(user);

        return new AuthenticationResponse(jwt);

    }
    
}
