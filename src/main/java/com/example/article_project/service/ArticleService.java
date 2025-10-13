package com.example.article_project.service;

import org.springframework.stereotype.Service;

import com.example.article_project.domain.Article;
import com.example.article_project.dto.ArticleDto;


@Service
public interface ArticleService {

    // 게시글 등록
    public Long registerArticle(ArticleDto articleDto);

    // default method
    default Article dtoToEntity(ArticleDto articleDto){
        return Article.builder()
            .title(articleDto.getTitle())
            .contents(articleDto.getContents())
            .writer(articleDto.getWriter())
            .regDate(articleDto.getRegDate())
            .build();
    }

    default ArticleDto entityToDto(Article article){
        return ArticleDto.builder()
            .title(article.getTitle())
            .contents(article.getContents())
            .writer(article.getWriter())
            .regDate(article.getRegDate())
            .build();
    }

}
