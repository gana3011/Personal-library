package com.example.graphqlex.service;

import com.example.graphqlex.dto.UserDto;
import com.example.graphqlex.dto.UserResponseDto;
import com.example.graphqlex.models.User;
import com.example.graphqlex.repository.UserRepository;
import graphql.GraphQLException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private RedisService redisService;

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
        try {
            redisService.save("refresh:" + user.getId().toString(), refresh, jwtService.refreshExpiration);
        } catch (Exception e) {
            System.out.printf("Failed to save refresh token in Redis for user {}", user.getId(), e);
//            throw new IllegalStateException("Redis unavailable. Cannot save refresh token.");
        }

        return new UserResponseDto(user.getId(),user.getEmail(),access,refresh);
    }

    public String refresh(String authHeader){
        String refreshToken = authHeader.substring(7);
        Long userId = Long.parseLong(jwtService.extractUserId(refreshToken));
        try{
            User user = userRepo.findById(userId).orElseThrow(()->new UsernameNotFoundException("User not found"));
            String key = "refresh:" + user.getId().toString();
            String token = (String) redisService.get(key);
            if(token==null){
                throw new RuntimeException("Token missing");
            }

            if(!token.equals(refreshToken)){
                throw new RuntimeException("Invalid token");
            }

            boolean verify = jwtService.verifyRefreshToken(refreshToken, userId.toString());
            if(!verify){
                throw new RuntimeException("Invalid token");
            }
            return jwtService.generateAccessToken(user.getEmail());
        }

        catch (Exception e){
            throw new GraphQLException(e.getMessage());
        }

    }
}
