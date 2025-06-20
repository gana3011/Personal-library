package com.example.graphqlex.service;

import com.example.graphqlex.dto.UserDto;
import com.example.graphqlex.dto.UserResponseDto;
import com.example.graphqlex.models.User;
import com.example.graphqlex.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    public Long addUser(UserDto userDto){
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

    public UserResponseDto loginUser(UserDto userDto){
        User user = userRepo.findByEmail(userDto.getEmail()).orElseThrow(()->new RuntimeException("No user exists with the email"));
        String hashedPassword = user.getPassword();
        boolean matches = passwordEncoder.matches(userDto.getPassword(), hashedPassword);
        if(!matches){
            throw new BadCredentialsException("Invalid credentials");
        }
        String access = jwtService.generateAccessToken(userDto.getEmail());
        String refresh = jwtService.generateRefreshToken(user.getId().toString());
        return new UserResponseDto(user.getId(),user.getEmail(),access,refresh);
    }
}
