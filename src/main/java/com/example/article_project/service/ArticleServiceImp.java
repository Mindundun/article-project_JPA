package com.example.article_project.service;

import org.springframework.stereotype.Service;

import com.example.article_project.domain.Article;
import com.example.article_project.dto.ArticleDto;
import com.example.article_project.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleServiceImp implements ArticleService {

    private final ArticleRepository articleRepository;

    @Override
    public Long registerArticle(ArticleDto articleDto) {
        Article article = dtoToEntity(articleDto);
        articleRepository.save(article);
        return article.getId();
    }
    
}
