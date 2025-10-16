package com.example.article_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.article_project.domain.Article;

// Spring Data Jpa 
@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> , CustomArticleRepository{
    
    @Query("SELECT a FROM Article AS a WHERE a.id = :articleId")
    Article findArticleById(@Param("articleId") Long id);

    @Query("SELECT size(a.files) FROM Article AS a WHERE a.id = :articleId")
    int getFileCount(@Param("articleId") Long id);

    @Query("SELECT a FROM Article AS a JOIN Fetch a.files AS f WHERE a.id = :articleId AND INDEX(f) = 0")
    Article findArticleWithFirstFile(@Param("articleId") Long id);

    
}
