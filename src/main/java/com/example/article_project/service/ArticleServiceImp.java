package com.example.article_project.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.article_project.domain.Article;
import com.example.article_project.dto.ArticleDto;
import com.example.article_project.repository.ArticleRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleServiceImp implements ArticleService {

    private final ArticleRepository articleRepository;

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
