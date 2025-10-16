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
import lombok.extern.slf4j.Slf4j;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isNotNull;

import java.time.LocalDateTime;
import java.util.List;


@SpringBootTest
@Transactional
@Slf4j
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

        // List<Article> articles = queryFactory.select(qArticle)
        //                                     .from(qArticle)
        //                                     .fetch();


        // ****************************************

        // List<Article> articles = queryFactory.selectFrom(qArticle)
        //                                     .where(qArticle.title.contains("빢빢")) // .where(qArticle.title.like("%title%"))
        //                                     .fetch();

        // log.info("articles : {}", articles.size());

        // ****************************************

        List<Article> articles = queryFactory.selectFrom(qArticle)
                                            .where(qArticle.title.contains("title").and(qArticle.contents.contains("content")))
                                            .orderBy(qArticle.id.desc())
                                            .fetch();

        log.info("articles first Id : {}", articles.get(0).getId());
        log.info("articles last Id : {}", articles.get(articles.size() - 1).getId());
        log.info("articles : {}", articles.size());


        // Then
        // assertThat(articles).hasSize(252);
        assertThat(articles).isNotNull();
    }

    @Test
    void test1() {
        // Given
        Long articleId = 281L;

        // Q 클래스
        QArticle qArticle = QArticle.article;

        JPAQueryFactory queryFactory = new JPAQueryFactory(em); 

        // When
        Article article = queryFactory.selectFrom(qArticle)
                                        .where(qArticle.id.eq(articleId))
                                        .fetchOne();
        
        // Then
        assertThat(article).isNotNull();

    }


}
