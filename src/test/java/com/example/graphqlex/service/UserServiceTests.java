package com.example.graphqlex.service;

import com.example.graphqlex.dto.UserDto;
import com.example.graphqlex.models.User;
import com.example.graphqlex.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserServiceTests {
    @Mock
    UserRepository userRepo;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser_UserNotExists(){
        UserDto userDto = new UserDto("Test", "test@gmail.com", "test");
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setId(1L);

        when(userRepo.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn(new String("hashed"));
        when(userRepo.save(any(User.class))).thenReturn(user);

        Long result = userService.addUser(userDto);

        assertNotNull(result);
        assertEquals(user.getId(),result);

        verify(userRepo).save(any(User.class));
        verify(userRepo).findByEmail(userDto.getEmail());
        verify(passwordEncoder).encode(userDto.getPassword());
    }

    @Test
    void testAddUser_UserExists(){
        UserDto userDto = new UserDto("Test", "test@gmail.com", "test");
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setId(1L);

        when(userRepo.findByEmail(userDto.getEmail())).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, ()-> userService.addUser(userDto));
    }
}
