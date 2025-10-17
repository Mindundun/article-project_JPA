package com.example.article_project.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.article_project.domain.Article;
import com.example.article_project.dto.ArticleDto;
import com.example.article_project.dto.ArticleSearchCondition;
import com.example.article_project.dto.PageRequestDto;
import com.example.article_project.dto.PageResponseDto;


@Service
public interface ArticleService {

    // 검색과 페이징 처리
    PageResponseDto<ArticleDto> search(ArticleSearchCondition condition, PageRequestDto pageRequestDto);

    // 페이징 처리
    PageResponseDto<ArticleDto> paging(PageRequestDto pageRequestDto);

    // 게시글 등록
    public Long registerArticle(ArticleDto articleDto);

    // 게시글 상세 조회
    public ArticleDto retrieveArticle(Long id);

    // 게시글 수정
    public void modifyArticle(ArticleDto articleDto);

    // 게시글 삭제
    public void removeArticle(Long id);

    // default method
    default Article dtoToEntity(ArticleDto articleDto){
        return Article.builder()
            .id(articleDto.getId())
            .title(articleDto.getTitle())
            .contents(articleDto.getContents())
            .writer(articleDto.getWriter())
            .regDate(articleDto.getRegDate())
            .build();
    }

    default ArticleDto entityToDto(Article article){
        return ArticleDto.builder()
            .id(article.getId())
            .title(article.getTitle())
            .contents(article.getContents())
            .writer(article.getWriter())
            .regDate(article.getRegDate())
            .build();
    }

}
