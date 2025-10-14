package com.example.article_project.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Value Object : 값 객체
@Embeddable
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {

    private String fileName;
    private String filePath;
    private Long fileSize;
    
}
