package com.example.graphqlex.controller;

import com.example.graphqlex.dto.UserDto;
import com.example.graphqlex.dto.UserResponseDto;
import com.example.graphqlex.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@GraphQlTest(UserController.class)
class UserControllerTests {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockitoBean
    private UserService userService;

    @Test
    void testAddUser() {
        UserDto input = new UserDto("Test", "test@gmail.com", "password");
        when(userService.addUser(any(UserDto.class))).thenReturn(1L);

        String mutation = """
            mutation {
              addUser(input: {
                name: "Test",
                email: "test@gmail.com",
                password: "password"
              })
            }
        """;

        graphQlTester.document(mutation)
                .execute()
                .path("addUser")
                .entity(Long.class)
                .isEqualTo(1L);

        verify(userService).addUser(any(UserDto.class));
    }

    @Test
    void testLoginUser() {
        UserDto input = new UserDto("Test", "test@gmail.com", "password");
        UserResponseDto responseDto = new UserResponseDto(1L, "test@gmail.com", "access","refresh");

        when(userService.loginUser(any(UserDto.class))).thenReturn(responseDto);

        String query = """
            query {
              loginUser(input: {
                email: "test@gmail.com",
                password: "password"
              }) {
                id
                email
                access
                refresh
              }
            }
        """;

        graphQlTester.document(query)
                .execute()
                .path("loginUser.id").entity(Long.class).isEqualTo(1L)
                .path("loginUser.email").entity(String.class).isEqualTo("test@gmail.com")
                .path("loginUser.access").entity(String.class).isEqualTo("access")
                .path("loginUser.refresh").entity(String.class).isEqualTo("refresh");

        verify(userService).loginUser(any(UserDto.class));
    }
}


