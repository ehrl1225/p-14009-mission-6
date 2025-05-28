package com.back.step15.repository;

import com.back.step15.WiseSaying;

import java.io.*;

public class FileManager {
    final static String JSON_FOLDER = "./db/wiseSaying/";
    final static String FINAL_ID_FILE = "./db/wiseSaying/lastId.txt";
    final static String JSON_FILE = "./db/wiseSaying/data.json";

    JsonManager jsonManager;

    FileManager() {
        jsonManager = new JsonManager();
    }

    /**
     * save one wiseSaying as json file
     * @param wiseSaying
     * @throws IOException
     */
    public void saveWiseSayingAsJson(WiseSaying wiseSaying)  {
        try(FileOutputStream output = new FileOutputStream(JSON_FOLDER + wiseSaying.getId() +".json");){
            output.write(jsonManager.WiseSaying2json(wiseSaying).getBytes());
        }catch (IOException e){
            System.out.println("error while saving wiseSaying");
        }
    }


    /**
     * save wise sayings to data.json file
     * @param iterator
     */
    public void saveWiseSayingsAsOneJson(WiseSayingList.WiseSayingIterator iterator){
        try{
            try(FileOutputStream output = new FileOutputStream(JSON_FILE);){
                output.write(jsonManager.wiseSaying2json(iterator).getBytes());
            }
        } catch (IOException e) {
            System.out.println("Error saving wise saying");
        }
    }



}
