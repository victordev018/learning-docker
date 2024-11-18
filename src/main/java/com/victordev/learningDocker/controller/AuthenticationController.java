package com.victordev.learningDocker.controller;

import com.victordev.learningDocker.model.User;
import com.victordev.learningDocker.model.UserRole;
import com.victordev.learningDocker.model.dto.AuthenticationDTO;
import com.victordev.learningDocker.model.dto.LoginResponseDTO;
import com.victordev.learningDocker.model.dto.RegisterDTO;
import com.victordev.learningDocker.repository.UserRepository;
import com.victordev.learningDocker.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        Authentication authenticate = this.authenticationManager.authenticate(usernamePassword);

        String token = this.tokenService.generateToken((User) authenticate.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO request) {

        String password = new BCryptPasswordEncoder().encode(request.password());
        User user = new User(request.username(), password, UserRole.USER);
        this.userRepository.save(user);
        return ResponseEntity.noContent().build();
    }
}
