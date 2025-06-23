package com.example.graphqlex.integration;

import com.example.graphqlex.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;


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
}
