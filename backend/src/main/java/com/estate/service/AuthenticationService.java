package com.estate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.estate.authentication.AuthenticationRequest;
import com.estate.authentication.AuthenticationResponse;
import com.estate.authentication.RegisterRequest;
import com.estate.exception.ResourceNotFoundException;
import com.estate.model.User;
import com.estate.repository.UserRepository;

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
        User user = new User(
            request.getEmail(),
            request.getName(),
            passwordEncoder.encode(request.getPassword())
        );

        userRepository.save(user);

        String jwt = jwtService.generateToken(user);

        return new AuthenticationResponse(jwt);
            
    }

    public AuthenticationResponse login(AuthenticationRequest request) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        // todo: throw credentials error

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

        String jwt = jwtService.generateToken(user);

        return new AuthenticationResponse(jwt);

    }
    
}
