package com.example.article_project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.article_project.domain.Article;
import com.example.article_project.domain.Attachment;
import com.example.article_project.dto.ArticleSearchCondition;

import lombok.extern.slf4j.Slf4j;

@Import(CustomArticleRepositoryImpl.class)
@Slf4j
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ArticleRepositoryTest {
    @Autowired
    private ArticleRepository articleRepository;
    
    @Test
    @Rollback(false)
    void testSave() {
        // 게시글 등록
        // Given
        List<Article> articles = new ArrayList<>();
        
        for(int i = 0; i<125; i++){
            
            Article article = new Article();
            article.setTitle("박title"+(i+1));
            article.setContents("박Contents"+(i+1));
            article.setWriter("박Writer"+(i+1));
            article.setRegDate(LocalDateTime.now());
            articles.add(article);
            
            
        }
        // When
        articleRepository.saveAll(articles);

        // Then
        assertThat(articles).hasSize(125);
            
    }

    @Test
    void testFindById(){
        // Given
        Long articleId = 123L;

        // When
        Optional<Article> found = articleRepository.findById(articleId);

        if (found.isPresent()) {
            Article article = found.get();
            log.info("id : {}, title : {}, contens : {}, writer : {}", article.getId(),article.getTitle(),article.getContents(),article.getWriter());
        } else {
            log.info("{}에 해당하는 게시글이 존재하지 않습니다.", articleId);
        }
    }

    @Test
    void testFindById1(){
        // Given
        Long articleId = 200L;

        // When
        // Optional<Article> found = articleRepository.findById(articleId);

        assertThatThrownBy(() -> {

            Article article = articleRepository.findById(articleId)
                                                .orElseThrow(() -> new IllegalArgumentException(articleId + "에 해당하는 게시글이 존재하지 않습니다."));
            // 위에서 exception 미 발생 시 아래의 내용 출력
            log.info("id : {}, title : {}, contens : {}, writer : {}", article.getId(),article.getTitle(),article.getContents(),article.getWriter());

        }).isInstanceOf(IllegalArgumentException.class);

        // Then
    }

    @Test
    void testUpdate(){
        // Given
        Long articleId = 201L;

        // When
        Article article = articleRepository.findById(articleId).get();

        // 더티 체킹 . . set만 했을 뿐인데 update가 되어버림
        article.setContents("수정한 박 contents");

        // Then
        log.info("article : {} " ,article.getContents());
    }

    @Test
    @Rollback(false)
    void testDelete(){
        // Given
        Long articleId = 209L;
        
        // When
        articleRepository.deleteById(articleId);

        // Then
        Optional<Article> found = articleRepository.findById(articleId);
        
        assertThat(found).isNotNull();
        
        assertThatThrownBy(() -> {

            articleRepository.findById(articleId)
                              .orElseThrow(() -> new IllegalArgumentException(articleId + "에 해당하는 게시글이 존재하지 않습니다."));
            
        }).isInstanceOf(IllegalArgumentException.class);
        
    }

    @Test
    @Rollback(false)
    void saveArticleAndFile(){
        // Given    

        List<Attachment> files = new ArrayList<>();
        files.add(new Attachment("A.txt","/upload",1007L));
        files.add(new Attachment("B.txt","/upload",2007L));
        files.add(new Attachment("C.txt","/upload",3007L));

        Article article = Article.builder()
                            .title("title")
                            .contents("contents")
                            .writer("writer")
                            .files(files)
                            .build();
        
        // When
        articleRepository.save(article);

        // Then
        Long id = article.getId();
        assertThat(id).isNotNull();

    }

    @Test
    void testFindArticleById(){
        // Given
        Long id = 210L;

        // When
        Article article = articleRepository.findArticleById(id);

        article.getFiles().forEach(file -> log.info("Files : {} ",file));

        // Then
        assertThat(article.getId()).isNotNull();

        assertThat(article.getFiles()).hasSize(3);

    }

    @Test
    void testGetFileCount() {
        // Given
        Long id = 210L;

        // When
        int num = articleRepository.getFileCount(id);

        
        // Then
        assertThat(num).isNotNull();

        assertThat(num).isEqualTo(3);
    }

    @Test
    void testFindArticleWithFirstFile(){
        // Given
        Long id = 210L;

        // When
        Article article = articleRepository.findArticleWithFirstFile(id);

        log.info(article.getFiles().toString());
        
        // Then
        assertThat(article.getId()).isNotNull();
        assertThat(article.getFiles()).hasSize(1);

    }

    @Test
    void testSearch() {
        // Given

        ArticleSearchCondition condition = new ArticleSearchCondition();
        condition.setWriter("writer");

        Pageable pageable = PageRequest.of(1, 10);


        // When
        Page<Article> page = articleRepository.search(condition, pageable);

        // Then
        log.info("게시글 수 : {}", page.getTotalElements());
    }
}
