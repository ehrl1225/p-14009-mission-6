package com.back.domain.wiseSaying.repository;

import com.back.domain.wiseSaying.entity.WiseSaying;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class WiseSayingDatabase {
    ArrayList<WiseSaying> wiseSayings;
    int lastId;

    public WiseSayingDatabase() {
        wiseSayings = new ArrayList<>();
        lastId = 0;
    }

    public WiseSaying insert(String content, String author) {
        WiseSaying wiseSaying = new WiseSaying(++lastId, content, author);
        wiseSayings.add(wiseSaying);
        return wiseSaying;
    }

    public boolean removeWiseSaying(int id) {
        for (WiseSaying wiseSaying : wiseSayings) {
            if (wiseSaying.getId() == id) {
                wiseSayings.remove(wiseSaying);
                return true;
            }
        }
        return false;
    }

    public WiseSaying getWiseSaying(int id) {
        for (WiseSaying wiseSaying : wiseSayings) {
            if (wiseSaying.getId() == id) {
                return wiseSaying;
            }
        }
        return null;
    }

    public void updateWiseSaying(WiseSaying wiseSaying) {
        for (WiseSaying ws : wiseSayings) {
            if (ws.getId() == wiseSaying.getId()) {
                ws.setContent(wiseSaying.getContent());
                ws.setAuthor(wiseSaying.getAuthor());
                return;
            }
        }
    }
}
