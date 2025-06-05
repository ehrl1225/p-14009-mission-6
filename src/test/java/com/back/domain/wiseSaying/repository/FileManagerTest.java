package com.back.domain.wiseSaying.repository;

import com.back.domain.wiseSaying.entity.WiseSaying;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

public class FileManagerTest {

    @BeforeEach
    void setUp() {
        FileManager fileManager = new FileManager();
        fileManager.deleteAllWiseSaying();
    }

    @Test
    @DisplayName("save wise saying")
    void t1() throws IOException {
        WiseSaying wiseSaying = new WiseSaying(1, "a", "b");
        FileManager fileManager = new FileManager();
        fileManager.saveWiseSaying(wiseSaying);
    }

    @Test
    @DisplayName("load wise saying")
    void t2() throws IOException {
        WiseSaying wiseSaying = new WiseSaying(1, "a", "b");
        FileManager fileManager = new FileManager();
        fileManager.saveWiseSaying(wiseSaying);

        WiseSaying ws = fileManager.loadWiseSaying(1);
        assert(ws != null);
        assert(ws.getId() == 1);
        assert(ws.getContent().equals("a"));
        assert(ws.getAuthor().equals("b"));
    }

    @Test
    @DisplayName("load all wise saying")
    void t3() throws IOException {
        WiseSaying wiseSaying = new WiseSaying(1, "a", "b");
        WiseSaying wiseSaying2 = new WiseSaying(2, "c", "d");
        WiseSaying wiseSaying3 = new WiseSaying(3, "e", "f");
        WiseSaying wiseSaying4 = new WiseSaying(4, "g", "h");
        ArrayList<WiseSaying> wiseSayings;
        FileManager fileManager = new FileManager();
        fileManager.saveWiseSaying(wiseSaying);
        fileManager.saveWiseSaying(wiseSaying2);
        fileManager.saveWiseSaying(wiseSaying3);
        fileManager.saveWiseSaying(wiseSaying4);


        wiseSayings = fileManager.loadWiseSayings();
        assert(wiseSayings != null);

        int index = 0;
        for (WiseSaying ws : wiseSayings) {
            index++;
            assert(ws.getId() == index);
        }
    }

    @Test
    @DisplayName("save Id")
    void t4() throws IOException {
        FileManager fileManager = new FileManager();
        fileManager.saveLastId(3);
    }

    @Test
    @DisplayName("load Id")
    void t5() throws IOException {
        FileManager fileManager = new FileManager();
        fileManager.saveLastId(3);
        int id = fileManager.loadLastId();
        assert(id == 3);
    }
}
