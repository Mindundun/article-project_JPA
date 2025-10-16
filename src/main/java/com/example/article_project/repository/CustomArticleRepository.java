package com.example.article_project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.article_project.domain.Article;
import com.example.article_project.dto.ArticleSearchCondition;

public interface CustomArticleRepository {
    // 검색 조건과 페이지 처리
    Page<Article> search(ArticleSearchCondition condition, Pageable pageable);

    
} 
