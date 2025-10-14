package com.example.article_project.domain;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "article")
public class Article {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String contents;

    private String writer;

    // 1 : N 관계
    @ElementCollection
    @CollectionTable(name="attachment", joinColumns = @JoinColumn(name="id")) // id는 위의 Article.id
    @OrderColumn(name = "order_index")
    @Builder.Default
    List<Attachment> files = new ArrayList<>();
    
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    // 비즈니스 메소드
    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContents(String contents){
        this.contents = contents;
    }

    public void changeWriter(String writer){
        this.writer = writer;
    }

    
}
