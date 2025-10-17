package com.example.article_project.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.article_project.domain.Article;
import com.example.article_project.dto.ArticleDto;
import com.example.article_project.dto.ArticleSearchCondition;
import com.example.article_project.dto.PageRequestDto;
import com.example.article_project.dto.PageResponseDto;
import com.example.article_project.repository.ArticleRepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleServiceImp implements ArticleService {

    private final ArticleRepository articleRepository;

    

    @Override
    public PageResponseDto<ArticleDto> search(ArticleSearchCondition condition, PageRequestDto pageRequestDto) {
        // 페이지는 0부터 나와서 -1처리
        Pageable pageable = PageRequest.of(pageRequestDto.getPage() -1, pageRequestDto.getSize());

        Page<Article> page = articleRepository.search(condition, pageable);

        List<ArticleDto> articles = page.getContent().stream().map(article -> entityToDto(article)).collect(Collectors.toList());

        int totalCount = (int)page.getTotalElements();
        log.info("pageable : {} " , pageable);
        log.info("page : {} " , page);
        log.info("articles : {} " , articles);

        log.info("제바레바레자ㅔ바렞 : {}", PageResponseDto.<ArticleDto>builder()
                                .dtoList(articles)
                                .pageRequestDto(pageRequestDto)
                                .totalCount(totalCount)
                                .build());

        return PageResponseDto.<ArticleDto>builder()
                                .dtoList(articles)
                                .pageRequestDto(pageRequestDto)
                                .totalCount(totalCount)
                                .build();
    }

    @Override
    public PageResponseDto<ArticleDto> paging(PageRequestDto pageRequestDto) {

        Pageable pageable = PageRequest.of(pageRequestDto.getPage() -1, pageRequestDto.getSize(), Sort.by("id").descending());

        Page<Article> page = articleRepository.findAll(pageable);

        List<ArticleDto> posts = page.getContent().stream().map(article -> entityToDto(article)).collect(Collectors.toList()); // 람다식 이용
        // List<ArticleDto> posts = page.getContent().stream().map(this::entityToDto).collect(Collectors.toList()); // 메소드 레퍼런스 이용

        int totalCount = (int)page.getTotalElements(); // 전체 게시글 수

        return PageResponseDto.<ArticleDto>builder()
            .dtoList(posts)
            .pageRequestDto(pageRequestDto)
            .totalCount(totalCount)
            .build();
    }

    @Override
    @Transactional(readOnly = false)
    public Long registerArticle(ArticleDto articleDto) {
        Article article = dtoToEntity(articleDto);
        articleRepository.save(article);
        return article.getId();
    }

    @Override
    public ArticleDto retrieveArticle(Long id) {
        Article article = articleRepository.findById(id)
                                            .orElseThrow(() -> {
                                                return new IllegalArgumentException(id + "에 해당하는 게시글이 존재하지 않습니다.");
                                            });

        ArticleDto articleDto = entityToDto(article);
        
        return articleDto;

        // 위의 코드를 아래와 같이 줄여쓸 수 있음
        // return articleRepository.findById(id)
        //                         .map(this::entityToDto) // 메소드 참조
        //                         .orElseThrow(() -> {
        //                             return new IllegalArgumentException(id + "에 해당하는 게시글이 존재하지 않습니다.");
        //                         });

        // 위의 코드를 아래와 같이 줄여쓸 수 있음
        // return articleRepository.findById(id)
        //                         .map(article -> entityToDto(article)) // 람다
        //                         .orElseThrow(() -> {
        //                             return new IllegalArgumentException(id + "에 해당하는 게시글이 존재하지 않습니다.");
        //                         });
    }
    
    @Override
    @Transactional(readOnly = false)
    public void modifyArticle(ArticleDto articleDto){
        Long articleId = articleDto.getId();

        Article article = articleRepository.findById(articleId)
                            .orElseThrow(() -> new IllegalArgumentException("해당하는 "+articleId+"의 게시글이 없습니다."));

        article.setTitle(articleDto.getTitle());
        article.setContents(articleDto.getContents());
        article.setWriter(articleDto.getWriter());
    }

    @Override
    @Transactional(readOnly = false)
    public void removeArticle(Long id){

        articleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(id+"에 해당하는 게시글이 존재하지 않습니다."));

        articleRepository.deleteById(id);
        
    }
}
