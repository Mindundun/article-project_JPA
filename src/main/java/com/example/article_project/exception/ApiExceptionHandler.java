package com.example.article_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    // Exception handler method
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest req, Exception ex){

        log.error("uri : {}, method : {}, ErrorMessage : {}" , req.getRequestURI() , req.getMethod() , ex.getMessage());
        // System.out.println("uri : " + req.getRequestURI() + ", method : " + req.getMethod() + ", ErrorMessage : " + ex.getMessage());

        // Builder 패턴
        ErrorResponse response = ErrorResponse.builder()
                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                                    .message(ex.getMessage())
                                    .build();

        return ResponseEntity.ok().body(response);
    }

    // Exception handler method : handleException1
    // 메소드 오버로딩
    @ExceptionHandler(value = ArticleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest req, ArticleNotFoundException ex){

        log.error("uri : {}, method : {}, ErrorMessage : {}" , req.getRequestURI() , req.getMethod() , ex.getMessage());
        // System.out.println("uri : " + req.getRequestURI() + ", method : " + req.getMethod() + ", ErrorMessage : " + ex.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                                    .code(String.valueOf(HttpStatus.BAD_REQUEST.value())) // → "400" 
                                    //.code(HttpStatus.BAD_REQUEST.toString()) // → "400 BAD_REQUEST"
                                    .message(ex.getMessage())
                                    .build();


        return ResponseEntity.ok().body(response);
    }
    
}
