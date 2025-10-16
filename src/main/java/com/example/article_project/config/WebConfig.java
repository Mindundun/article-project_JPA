package com.example.article_project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//WebMvcConfigurer : Spring MVC 설정을 커스터마이징할때 사용하는 인터페이스

public class WebConfig implements WebMvcConfigurer{

    @Override
    public void addCorsMappings(CorsRegistry registry) {
     
        registry.addMapping("/**") // 특정 경로 매핑
                .allowedOrigins("*")  // 허용할 도메인 (*)               
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
                // .allowCredentials(true); // 쿠키 인증 정보 허용 여부
    }

}
