package com.back.domain.wiseSaying.repository;

import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.simpleDb.SimpleDb;

import java.io.IOException;
import java.util.List;

public class WiseSayingRepository {

    MySqlDatabase mySqlDatabase;
    FileManager fileManager;

    public WiseSayingRepository() {
        fileManager = new FileManager();
        mySqlDatabase = new MySqlDatabase(new SimpleDb("localhost", "root", "lldj123414", "WiseSaying"));
    }

    public int insert(String content, String author) {
        WiseSaying wiseSaying = mySqlDatabase.insert(content, author);
        return wiseSaying.getId();
    }

    public List<WiseSaying> selectAll() {
        return mySqlDatabase.findAll();
    }

    public List<WiseSaying> selectByAuthor(String author) {
        return mySqlDatabase.findByAuthor(author);
    }

    public List<WiseSaying> selectByContent(String content) {
        return mySqlDatabase.findByContent(content);
    }

    public List<WiseSaying> selectByAuthorOrContent(String author, String content) {
        return mySqlDatabase.findByAuthorAndContent(author, content);
    }

    public boolean delete(int id){
        return mySqlDatabase.delete(id);
    }

    public WiseSaying selectById(int id) {
        return mySqlDatabase.findById(id);
    }

    public void update(WiseSaying wiseSaying) {
        mySqlDatabase.update(wiseSaying);
    }

    public void close() {

    }

    public void build() throws IOException {
        fileManager.buildWiseSayings(mySqlDatabase.findAll());
    }
}
