package com.example.graphqlex.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserResponseDto {
        private Long id;
        private String email;
        private String access;
        private String refresh;
}
