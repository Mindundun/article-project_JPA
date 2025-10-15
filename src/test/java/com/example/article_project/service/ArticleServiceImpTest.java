package com.example.article_project.service;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.article_project.dto.ArticleDto;
import com.example.article_project.dto.PageRequestDto;
import com.example.article_project.dto.PageResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ArticleServiceImpTest {

    @Autowired
    private ArticleService articleService;

    @Test
    void testRegisterArticle() {

        // Given
        ArticleDto articleDto = ArticleDto.builder()
                                    .title("박title!!")
                                    .contents("박contents!!")
                                    .writer("박Writer!!")
                                    .regDate(LocalDateTime.now())
                                    .build();
        
        // When
        Long id = articleService.registerArticle(articleDto);
        log.info("id : {}", id);

        // Then
        assertThat(id).isNotNull();
        
    }

    @Test
    void testRetrieveArticle() {

        // Given
        Long id = 201L;
        
        // When
        ArticleDto articleDto = articleService.retrieveArticle(id);

        log.info("articleDto : {}", articleDto.toString());

        // Then
        assertThat(articleDto).isNotNull();
        
    }

    @Test
    @Rollback(false)
    void modifyArticle() {

        // Given
        ArticleDto articleDto = ArticleDto.builder()
            .id(201L)
            .title("수정할거다")
            .contents("수정한 박contest~!")
            .writer("문영미")
            .build();

        // When
        articleService.modifyArticle(articleDto);

        // Then
        ArticleDto found = articleService.retrieveArticle(articleDto.getId());

        assertThat(found).isNotNull();
        assertThat(found.getTitle()).isEqualTo("수정할거다");
    }

    // 페이징 처리
    @Test
    void testPaging(){

        // Given
        PageRequestDto pageRequestDto = PageRequestDto.builder()
                                                    .page(2)
                                                    .size(10)
                                                    .build();
        // When
        PageResponseDto<ArticleDto> page = articleService.paging(pageRequestDto);
        log.info("Page.TotalCount: {}", page.getTotalCount());
        log.info("Page : {} , size : {}", page.getPageRequestDto().getPage(), page.getPageRequestDto().getSize());

        page.getDtoList().forEach(article -> log.info("article : {}", article.toString()));

        // Then
        assertThat(page.getDtoList()).hasSize(10);
        assertThat(page.getTotalCount()).isEqualTo(125);
    }
}
