package com.example.graphqlex.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long userid;
    private String name;
    private String author;
    private String status;
}
