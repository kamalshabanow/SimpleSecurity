package com.kamal.service;

import com.kamal.constant.UserRole;
import com.kamal.dto.request.CreateUserRequestDTO;
import com.kamal.model.AuthRequest;
import com.kamal.model.AuthResponse;
import com.kamal.model.User;
import com.kamal.repository.UserRepository;
import com.kamal.security.CustomUserDetailsService;
import com.kamal.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;


    public String createUser(CreateUserRequestDTO createUserRequestDTO){

        User user = new User();
        user.setUsername(createUserRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(createUserRequestDTO.getPassword()));
        user.setRole(UserRole.USER);
        userRepository.save(user);

        return "User registered";
    }

    public AuthResponse login(AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword())
        );

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenProvider.generateToken(authentication);

        return new AuthResponse(token,userDetails.getUsername(),userDetails.getAuthorities());
    }

    public User getUserById(String id){
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
