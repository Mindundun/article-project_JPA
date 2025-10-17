package com.example.article_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSearchCondition {
    
    
    private String title;
    private String contents;
    private String writer;

    
}
