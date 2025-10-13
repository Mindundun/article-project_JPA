package com.example.article_project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.article_project.dto.ArticleDto;
import com.example.article_project.service.ArticleService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ArticleController {
    
    private final ArticleService articleService;

    // 게시글 등록
    @PostMapping("/articles")
    public ResponseEntity<Map<String, Long>> postArticle(@RequestBody ArticleDto articleDto) {
        
        articleDto.setRegDate(LocalDateTime.now());
        
        Long id = articleService.registerArticle(articleDto);

        return new ResponseEntity(Map.of("articleId", id), HttpStatus.CREATED);
        // return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", id));
    }
    

}
