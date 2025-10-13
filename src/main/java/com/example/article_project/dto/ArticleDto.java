package com.example.article_project.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    
    private Long id;

    private String title;

    private String contents;

    private String writer;

    private LocalDateTime regDate;
}
