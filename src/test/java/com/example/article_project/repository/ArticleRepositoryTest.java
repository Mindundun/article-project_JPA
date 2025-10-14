package com.example.article_project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.article_project.domain.Article;
import com.example.article_project.dto.ArticleDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ArticleRepositoryTest {
    @Autowired
    private ArticleRepository articleRepository;
    
    @Test
    @Rollback(false)
    void testSave() {
        // 게시글 등록
        // Given
        List<Article> articles = new ArrayList<>();
        
        for(int i = 0; i<100; i++){
            
            Article article = new Article();
            article.setTitle("박title"+(i+1));
            article.setContents("박Contents"+(i+1));
            article.setWriter("박Writer"+(i+1));
            article.setRegDate(LocalDateTime.now());
            articles.add(article);
            
            
        }
        // When
        articleRepository.saveAll(articles);

        // Then
        assertThat(articles).hasSize(100);
            
    }

    @Test
    void testFindById(){
        // Given
        Long articleId = 123L;

        // When
        Optional<Article> found = articleRepository.findById(articleId);

        if (found.isPresent()) {
            Article article = found.get();
            log.info("id : {}, title : {}, contens : {}, writer : {}", article.getId(),article.getTitle(),article.getContents(),article.getWriter());
        } else {
            log.info("{}에 해당하는 게시글이 존재하지 않습니다.", articleId);
        }
    }

    @Test
    void testFindById1(){
        // Given
        Long articleId = 200L;

        // When
        // Optional<Article> found = articleRepository.findById(articleId);

        assertThatThrownBy(() -> {

            Article article = articleRepository.findById(articleId)
                                                .orElseThrow(() -> new IllegalArgumentException(articleId + "에 해당하는 게시글이 존재하지 않습니다."));
            // 위에서 exception 미 발생 시 아래의 내용 출력
            log.info("id : {}, title : {}, contens : {}, writer : {}", article.getId(),article.getTitle(),article.getContents(),article.getWriter());

        }).isInstanceOf(IllegalArgumentException.class);

        // Then
    }

    @Test
    void testUpdate(){
        // Given
        Long articleId = 201L;

        // When
        Article article = articleRepository.findById(articleId).get();

        // 더티 체킹 . . set만 했을 뿐인데 update가 되어버림
        article.setContents("수정한 박 contents");

        // Then
        log.info("article : {} " ,article.getContents());
    }

    @Test
    @Rollback(false)
    void testDelete(){
        // Given
        Long articleId = 209L;
        
        // When
        articleRepository.deleteById(articleId);

        // Then
        Optional<Article> found = articleRepository.findById(articleId);
        
        assertThat(found).isNotNull();
        
        assertThatThrownBy(() -> {

            articleRepository.findById(articleId)
                              .orElseThrow(() -> new IllegalArgumentException(articleId + "에 해당하는 게시글이 존재하지 않습니다."));
            
        }).isInstanceOf(IllegalArgumentException.class);
        
    }
}
