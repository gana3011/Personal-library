package com.example.graphqlex.controller;

import com.example.graphqlex.dto.UserDto;
import com.example.graphqlex.dto.UserResponseDto;
import com.example.graphqlex.models.User;
import com.example.graphqlex.repository.UserRepository;
import com.example.graphqlex.service.UserService;
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
    UserService userService;
    @MutationMapping
    public Long addUser(@Argument("input") UserDto userDto){
        return userService.addUser(userDto);
    }

    @QueryMapping
    public UserResponseDto loginUser(@Argument("input") UserDto userDto){
        return userService.loginUser(userDto);
    }

//    @QueryMapping
//    public String refresh(String refresh){
//        return userService.
//    }


}
