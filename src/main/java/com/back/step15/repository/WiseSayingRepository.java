package com.back.step15.repository;

import com.back.step15.WiseSaying;

public class WiseSayingRepository {
//    WiseSayingDatabase database;
    MySqlManager mysql;
    FileManager fileManager;

    public WiseSayingRepository() {
//        database = new WiseSayingDatabase();
//        database.loadWiseSayingList();
        mysql = new MySqlManager();
        mysql.connect();
        fileManager = new FileManager();
    }

    public void close(){
//        database.close();
        mysql.disconnect();
    }

    public int insertWiseSaying(WiseSaying wiseSaying) {
//        return database.insertWiseSaying(wiseSaying);
        return mysql.insertWiseSaying(wiseSaying.getContent(), wiseSaying.getAuthor());
    }

    public WiseSayingView showWiseSaying(int page) {
//        return database.getWiseSayingList(page);
        return mysql.selectPagedWiseSaying(page);
    }

    public void deleteWiseSaying(int id) {
//        database.deleteWiseSaying(id);
        mysql.deleteWiseSaying(id);
    }

    public void updateWiseSaying(WiseSaying wiseSaying) {
//        database.updateWiseSaying(wiseSaying);
        mysql.updateWiseSaying(wiseSaying.getId(), wiseSaying.getContent(), wiseSaying.getAuthor());
    }

    public void buildWiseSayingList() {
//        database.buildWiseSayingList();
        fileManager.saveWiseSayingsAsOneJson(mysql.selectAllWiseSaying().iterator());
    }

    public WiseSaying getWiseSaying(int id) {
//        return database.getWiseSaying(id);
        return mysql.selectWiseSayingByID(id);
    }

    public boolean hasWiseSaying(int id) {
//        return database.getWiseSaying(id) != null;
        return mysql.selectWiseSayingByID(id) != null;
    }

    public WiseSayingView searchWiseSayingList(String keywordType, String keyword, int page) {
        return switch (keywordType){
//            case "content" -> database.searchWiseSayingWithContent(keyword, page);
            case "content" -> mysql.selectWiseSayingByContent(page, keyword);
//            case "author" -> database.searchWiseSayingWithAuthor(keyword, page);;
            case "author" -> mysql.selectWiseSayingByAuthor(page, keyword);
            default -> null;
        };
    }

}
