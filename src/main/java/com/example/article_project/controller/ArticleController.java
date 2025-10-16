package com.example.article_project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.article_project.domain.Article;
import com.example.article_project.dto.ArticleDto;
import com.example.article_project.dto.PageRequestDto;
import com.example.article_project.dto.PageResponseDto;
import com.example.article_project.service.ArticleService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ArticleController {
    
    private final ArticleService articleService;

    // 페이징 조회
    @GetMapping("/articles")
    public ResponseEntity<PageResponseDto<ArticleDto>> paging(PageRequestDto pageRequestDto) {
        PageResponseDto<ArticleDto> pageResponseDto = articleService.paging(pageRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(pageResponseDto);
    }
    
    // 게시글 등록
    @PostMapping("/articles")
    public ResponseEntity<Map<String, Long>> postArticle(@RequestBody ArticleDto articleDto) {
        
        articleDto.setRegDate(LocalDateTime.now());
        
        Long id = articleService.registerArticle(articleDto);

        return new ResponseEntity(Map.of("articleId", id), HttpStatus.CREATED);
        // return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", id));
    }

    // 게시글 상세 조회
    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable(value="id") Long articleId) {

        ArticleDto articleDto = articleService.retrieveArticle(articleId);

        // @RestController 내 @ResponseBody : ArticleDto -> json
        return ResponseEntity.status(HttpStatus.OK).body(articleDto);
    }

    // 게시글 수정
    @PutMapping("/articles/{id}")
    public ResponseEntity<String> putArticle(@PathVariable(value="id") Long articleId, @RequestBody ArticleDto ArticleDto) {
        ArticleDto.setId(articleId);

        articleService.modifyArticle(ArticleDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
    
    // 게시글 삭제
    @DeleteMapping("/articles/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable(value="id") Long articleId) {
        
        articleService.removeArticle(articleId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
    

}
