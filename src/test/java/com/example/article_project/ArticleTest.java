package com.example.article_project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.article_project.domain.Article;
import com.example.article_project.domain.QArticle;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ArticleTest {
    @Autowired
    private EntityManager em;

    @Test
    void test(){
        // Given
        
        // Article is Entity
        Article article = Article.builder()
                                .title("빢빢영미 Querydsl!!")
                                .contents("빢빢이 영미")
                                .writer("민둥산영미")
                                .regDate(LocalDateTime.now())
                                .build();

        em.persist(article);

        // Q 클래스
        QArticle qArticle = QArticle.article;

        JPAQueryFactory queryFactory = new JPAQueryFactory(em); 

        // When

        List<Article> articles = queryFactory.select(qArticle)
                                            .from(qArticle)
                                            .fetch();

        // Article result = 


        // Then
        assertThat(articles).hasSize(252);


    }


}
