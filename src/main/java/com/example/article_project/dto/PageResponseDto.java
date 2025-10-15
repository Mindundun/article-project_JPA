package com.example.article_project.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class PageResponseDto<T> {
    
    private List<T> dtoList;

    private PageRequestDto pageRequestDto;

    private int totalCount; // 전체 게시글 수

    private boolean prev = false, next = false;

    private int start = 0, end = 0;

    private int prevPage = 0, nextPage = 0, totalPage = 0, currentPage = 0, size = 0;

    private int pageSize = 10; // 페이지 블록 수

    private List<Integer> pageNumList = new ArrayList<>(); // 페이지 번호



    @Builder
    public PageResponseDto(List<T> dtoList, PageRequestDto pageRequestDto, int totalCount) {
        this.dtoList = dtoList;
        this.pageRequestDto = pageRequestDto;
        this.totalCount = totalCount;

        this.currentPage = pageRequestDto.getPage();

        this.size = pageRequestDto.getSize();
        
        // 현재 페이지 번호가 속한 페이지 블록의 마지막 페이지 번호 계산
        end = (int)(Math.ceil(currentPage / (double)pageSize)) * pageSize;

        log.info("currentPage : {}", currentPage);
        log.info("pageSize : {}", pageSize);
        log.info("end1 : {}", end);

        start = end - (pageSize - 1);

        log.info("start : {}", start);

        // 총 페이지 수를 계산
        int lastPage = (int)(Math.ceil(totalCount / (double)size)) * size;

        log.info("lastPage : {}", lastPage);

        this.totalPage = lastPage;

        log.info("totalPage : {}", totalPage);

        // 끝 페이지 번호가 총 페이지 수를 넘지 않도록
        end = end > lastPage ? lastPage : end;

        log.info("end2 : {}", end);

        // 이전 / 다음 버튼 활성화 여부
        this.prev = start > 1;
        this.next = totalCount > (end * size);

        log.info("prev : {}", prev);
        log.info("next : {}", next);

        // 페이지 번호를 생성
        // IntStream -> Stream<Integer> -> List<Integer>
        this.pageNumList = IntStream.range(start, end + 1).boxed().collect(Collectors.toList());

        log.info("pageNumList : {}", pageNumList);

        // 이전(prev) 활성화된 경우 prevPage는 이전 페이지 블록의 마지막 페이지 번호로 이동
        // 이전(prev) 비활성화된 경우 prevPage는 0으로 설정
        this.prevPage = prev ? start - 1 : 0;

        log.info("prevPage : {}", prevPage);

        // 다음(next) 활성화된 경우 nextPage는 다음 페이지 블록의 첫 페이지 번호로 이동
        // 다음(next) 비활성화된 경우 nextPage는 0으로 설정
        this.nextPage = next ? end + 1 : 0;

        log.info("nextPage : {}", nextPage);

    }

}
