package com.example.graphqlex.integration;

import com.example.graphqlex.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
public class UserIntegrationTests extends IntegrationTestBase{

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void testAddUser(){
        String mutation = """
            mutation {
                addUser(input: {
                    name: "TestUser",
                    email: "test@user.com",
                    password: "password"
                })
            }
        """;

        graphQlTester.document(mutation)
                .execute()
                .path("addUser").hasValue();

        assertThat(userRepository.findByEmail("test@user.com")).isPresent();
    }

    @Test
    @Transactional
    void testLoginUser(){
            String query = """
            query {
                loginUser(input: {
                    email: "test@user.com",
                    password: "password"
                }){
                id
                email
                access
                refresh
              }
            }
        """;

        graphQlTester.document(query)
                .execute()
                .path("loginUser").hasValue();
    }
}
