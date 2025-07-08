package com.example.graphqlex.controller;

import com.example.graphqlex.dto.UserDto;
import com.example.graphqlex.dto.UserResponseDto;
import com.example.graphqlex.service.UserService;
import graphql.schema.DataFetchingEnvironment;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
        UserResponseDto res = userService.loginUser(userDto);
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getResponse();
        if (response != null) {
            Cookie cookie = new Cookie("refresh", res.getRefresh());
            cookie.setMaxAge(14 * 24 * 60 * 60);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return res;
    }

    @MutationMapping
    public String refresh(DataFetchingEnvironment env) {
        HttpServletRequest request = env.getGraphQlContext().get(HttpServletRequest.class);
        String authHeader = request.getHeader("Authorization");
        return userService.refresh(authHeader);
    }

}
