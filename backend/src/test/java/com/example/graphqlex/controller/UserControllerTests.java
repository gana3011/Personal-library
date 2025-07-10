package com.example.graphqlex.controller;

import com.example.graphqlex.dto.UserDto;
import com.example.graphqlex.dto.UserResponseDto;
import com.example.graphqlex.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        ServletRequestAttributes mockAttributes = mock(ServletRequestAttributes.class);
        when(mockAttributes.getResponse()).thenReturn(mockResponse);
        RequestContextHolder.setRequestAttributes(mockAttributes);

        UserResponseDto mockRes = new UserResponseDto();
        mockRes.setId(1L);
        mockRes.setName("Test User");
        mockRes.setAccess("access-token");
        mockRes.setRefresh("refresh-token");
        when(userService.loginUser(any(UserDto.class))).thenReturn(mockRes);

        graphQlTester.document("""
        mutation {
          loginUser(input: {
            email: "test@gmail.com",
            password: "password"
          }) {
            id
            name
            access
            refresh
          }
        }
    """)
                .execute()
                .path("loginUser.id").hasValue()
                .path("loginUser.access").hasValue();

        verify(mockResponse).addCookie(any(Cookie.class));
    }


}


