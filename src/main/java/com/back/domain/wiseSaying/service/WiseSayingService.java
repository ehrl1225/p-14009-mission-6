package com.back.domain.wiseSaying.service;

import com.back.domain.wiseSaying.dto.Page;
import com.back.domain.wiseSaying.dto.Pageable;
import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.repository.WiseSayingRepository;

import java.io.IOException;
import java.util.List;

public class WiseSayingService {
    WiseSayingRepository wiseSayingRepository;

    public WiseSayingService(WiseSayingRepository wiseSayingRepository) {
        this.wiseSayingRepository = wiseSayingRepository;
    }

    public int insert(String content, String author) throws IOException {
        return wiseSayingRepository.insert(content, author);
    }

    public boolean delete(int id) {
        return wiseSayingRepository.delete(id);
    }

    public WiseSaying selectById(int id) {
        return wiseSayingRepository.selectById(id);
    }

    public void update(int id, String content, String author) throws IOException {
        WiseSaying wiseSaying = new WiseSaying(id, content, author);
        wiseSayingRepository.update(wiseSaying);
    }

    public void build() throws IOException {
        wiseSayingRepository.build();
    }

    public List<WiseSaying> select(String keywordType, String keyword) {
        if (keyword == null) {
            return wiseSayingRepository.selectAll();
        }
        return switch (keywordType) {
            case "author" ->wiseSayingRepository.selectByAuthor(keyword);
            case "content" ->wiseSayingRepository.selectByContent(keyword);
            default -> wiseSayingRepository.selectByAuthorOrContent(keyword, keyword);
        };
    }

    public Page<WiseSaying> selectPage(String keywordType, String keyword, Pageable pageable) {
        List<WiseSaying> total = select(keywordType, keyword);
        int total_count = total.size();
        int page = pageable.getPage();
        int pageSize = pageable.getPageSize();
        int start = total_count - pageSize * page;
        if (start < 0) {
            start = 0;
        }
        int end = total_count - pageSize * (page-1);
        if (end < 0){
            end = 0;
        }

        List<WiseSaying> paged = total.subList(start, end);
        return new Page<>(total_count, page, pageSize, paged);
    }

}
