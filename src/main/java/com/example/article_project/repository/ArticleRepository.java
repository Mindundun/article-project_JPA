package com.example.article_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.article_project.domain.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long>{
    
}
