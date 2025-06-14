package com.example.graphqlex.controller;

import com.example.graphqlex.dto.UserDto;
import com.example.graphqlex.dto.UserResponseDto;
import com.example.graphqlex.models.User;
import com.example.graphqlex.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Controller
public class UserController {
    private final UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    @MutationMapping
    public Long addUser(@Argument("input") UserDto userDto){
        if(userRepo.findByEmail(userDto.getEmail()).isPresent()){
            throw new RuntimeException("User already exists");
        }
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = userRepo.save(user);
        return savedUser.getId();
    }

    @QueryMapping
    public UserResponseDto loginUser(@Argument("input") UserDto userDto){
        User user = userRepo.findByEmail(userDto.getEmail()).orElseThrow(()->new RuntimeException("No user exists with the email"));
        String hashedPassword = user.getPassword();
        boolean matches = passwordEncoder.matches(userDto.getPassword(), hashedPassword);
        if(!matches){
            throw new BadCredentialsException("Invalid credentials");
        }
        return new UserResponseDto(user.getId(),user.getName(),user.getEmail());
    }

}
