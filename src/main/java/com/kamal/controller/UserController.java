package com.kamal.controller;


import com.kamal.dto.request.CreateUserRequestDTO;
import com.kamal.model.AuthRequest;
import com.kamal.model.AuthResponse;
import com.kamal.model.User;
import com.kamal.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @PostMapping("/users/create")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequestDTO createUserRequestDTO){
        String res = userService.createUser(createUserRequestDTO);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/users/login")
    public ResponseEntity<AuthResponse> login (@RequestBody AuthRequest authRequest){
        AuthResponse response = userService.login(authRequest);

        return  ResponseEntity.ok(response);
    }

    @GetMapping("/users/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<User> getUserById(@PathVariable String id, Authentication authentication){
        User user = userService.getUserById(id);
        if (user == null || !user.getUsername().equals(authentication.getName())) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }


}
