package com.example.graphqlex.controller;

import com.example.graphqlex.dto.UserDto;
import com.example.graphqlex.dto.UserResponseDto;
import com.example.graphqlex.service.UserService;
import graphql.schema.DataFetchingEnvironment;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Controller
public class UserController {
    UserService userService;
    @MutationMapping
    public Long addUser(@Argument("input") UserDto userDto){
        return userService.addUser(userDto);
    }

    @MutationMapping
    public UserResponseDto loginUser(@Argument("input") UserDto userDto){
        return userService.loginUser(userDto);
    }

    @MutationMapping
    public String refresh(DataFetchingEnvironment env) {
        HttpServletRequest request = env.getGraphQlContext().get(HttpServletRequest.class);
        String authHeader = request.getHeader("Authorization");
        return userService.refresh(authHeader);
    }

}
