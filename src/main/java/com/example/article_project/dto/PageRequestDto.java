package com.example.article_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDto {
    @Builder.Default // 사용 안하면 0으로 초기화 된다..!
    private int page = 1;
    @Builder.Default
    private int size = 10;
}
