package com.back.step15;

import com.back.step15.repository.MySqlManager;

public class SqlTest {

    public static void main(String[] args) {
        MySqlManager mySqlManager = new MySqlManager();
        mySqlManager.connect();
//        mySqlManager.createTable();
        mySqlManager.insertWiseSaying( "c" , "d");
//        System.out.println(mySqlManager.selectAllWiseSaying(1));
//        mySqlManager.deleteWiseSaying(1);
//        mySqlManager.updateWiseSaying(1, "d", "E");
        System.out.println(mySqlManager.selectWiseSayingByContent(1,"d"));
        mySqlManager.disconnect();
    }
}
