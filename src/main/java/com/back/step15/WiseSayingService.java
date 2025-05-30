package com.back.step15;

import com.back.step15.repository.WiseSayingRepository;
import com.back.step15.repository.WiseSayingView;

public class WiseSayingService {
    private final WiseSayingRepository wiseSayingRepository;

    WiseSayingService() {
        this.wiseSayingRepository = new WiseSayingRepository();
    }

    public void close(){
        wiseSayingRepository.close();
    }

    public int insertWiseSaying(String content, String author) {
        WiseSaying wiseSaying = new WiseSaying(content, author);
        return wiseSayingRepository.insertWiseSaying(wiseSaying);
    }

    public WiseSayingView showWiseSayings(int page){
        return wiseSayingRepository.showWiseSaying(page);
    }

    public void deleteWiseSaying(int id) {
        wiseSayingRepository.deleteWiseSaying(id);
    }

    public void updateWiseSaying(int id, String content, String author) {
        WiseSaying wiseSaying = new WiseSaying(content, author);
        wiseSaying.setID(id);
        wiseSayingRepository.updateWiseSaying(wiseSaying);
    }

    public void buildWiseSayingList() {
        wiseSayingRepository.buildWiseSayingList();
    }

    public WiseSaying getWiseSaying(int id) {
        return wiseSayingRepository.getWiseSaying(id);
    }

    public boolean hasWiseSaying(int id) {
        return wiseSayingRepository.hasWiseSaying(id);
    }

    public WiseSayingView searchWiseSaying(String keywordType, String keyword, int page) {
        return wiseSayingRepository.searchWiseSayingList(keywordType, keyword, page);
    }

}
