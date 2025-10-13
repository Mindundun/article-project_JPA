package com.example.article_project.exception;

// Unchecked Exception
public class ArticleNotFoundException extends RuntimeException{

    public ArticleNotFoundException() {
        super("해당 게시글이 존재하지 않습니다.");
    }

    public ArticleNotFoundException(String msg) {
        super(msg);
    }
}
