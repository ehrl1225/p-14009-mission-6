package com.back.step15;

import com.back.step15.repository.MySqlManager;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    public void add(){
        assertEquals(30,30);
    }

    public static void clear(){
        File json = new File("./db/wiseSaying/data.json");
        if (json.exists()){
            json.delete();
        }
        MySqlManager mySqlManager = new MySqlManager();
        mySqlManager.connect();
        mySqlManager.clearWiseSaying();
        mySqlManager.resetID();
        mySqlManager.disconnect();

    }

    public static String run(String input){
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        App app = new App();
        app.run();
        return outputStream.toString();
    }

}
