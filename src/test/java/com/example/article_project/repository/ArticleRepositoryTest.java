package com.example.article_project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.article_project.domain.Article;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ArticleRepositoryTest {
    @Autowired
    private ArticleRepository articleRepository;
    
    @Test
    void testSave() {
        // 게시글 등록
        // Given
        Article article = new Article();
        article.setTitle("박title");
        article.setContents("박Contents");
        article.setWriter("박Writer");

        // When
        articleRepository.save(article);

        log.info("id : {}", article.getId());

        // Then
        assertThat(article.getId()).isNotNull();

    }
}
