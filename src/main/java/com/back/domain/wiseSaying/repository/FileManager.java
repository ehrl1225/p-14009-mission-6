package com.back.domain.wiseSaying.repository;

import com.back.domain.wiseSaying.entity.WiseSaying;

import java.io.*;
import java.util.*;

public class FileManager {
    private final static String JSON_FOLDER = "./db/wiseSaying";


    public FileManager(){}

    private String wiseSaying2JsonString(WiseSaying wiseSaying){
        return String.format("""
                {
                  "id": %d,
                  "content": "%s",
                  "author": "%s"
                }
                """, wiseSaying.getId(), wiseSaying.getContent(), wiseSaying.getAuthor());

    }

    public void saveWiseSaying(WiseSaying wiseSaying) throws IOException {
        FileOutputStream output = new FileOutputStream(String.format("%s/%d.json", JSON_FOLDER, wiseSaying.getId()));
        String json = wiseSaying2JsonString(wiseSaying);
        output.write(json.getBytes());
        output.close();
    }

    public void saveWiseSayings(ArrayList<WiseSaying> wiseSayings) throws IOException {
        for (WiseSaying ws : wiseSayings) {
            saveWiseSaying(ws);
        }
    }

    private WiseSaying readWiseSaying(String json) {
        int start = json.indexOf("{");
        int end = json.lastIndexOf("}");
        String[] key_values = json.substring(start + 1, end).split(",");
        Map<String, String> map = new HashMap<>();
        for (String key_value : key_values) {
            key_value = key_value.replace('\"', ' ');
            String[] kv = key_value.split(":");
            if (kv.length != 2) {
                continue;
            }
            map.put(kv[0].trim(), kv[1].trim());
        }
        if (!map.containsKey("id")){
            return null;
        }
        int id;
        try{
             id = Integer.parseInt(map.get("id"));
        }catch (NumberFormatException e){
            return null;
        }
        if (!map.containsKey("content")){
            return null;
        }
        String content = map.get("content");
        if (!map.containsKey("author")){
            return null;
        }
        String author = map.get("author");
        return new WiseSaying(id,content,author);
    }

    public WiseSaying loadWiseSaying(int id) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(String.format("%s/%d.json", JSON_FOLDER, id)));
        StringBuilder sb = new StringBuilder();
        while (br.ready()) {
            sb.append(br.readLine());
        }
        br.close();
        String json = sb.toString();
        return readWiseSaying(json);
    }

    public void deleteWiseSaying(int id) {
        File file = new File(String.format("%s/%d.json", JSON_FOLDER, id));
        if (file.exists()) {
            file.delete();
        }
    }

    public void deleteAllWiseSaying() {
        File[] files = new File(JSON_FOLDER).listFiles();
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public ArrayList<WiseSaying> loadWiseSayings() {
        File[] files = new File(JSON_FOLDER).listFiles();
        ArrayList<Integer> file_ids = new ArrayList<>();
        for (File file : files) {
            String fileName = file.getName();
            if (fileName.endsWith(".json")) {
                file_ids.add(Integer.parseInt(fileName.substring(0, fileName.length() - 5)));
            }
        }
        Collections.sort(file_ids);
        ArrayList<WiseSaying> wiseSayings = new ArrayList<>();
        try{
            for (int id : file_ids) {
                wiseSayings.add(loadWiseSaying(id));
            }

        }catch (IOException e){
            wiseSayings = null;

        }
        return wiseSayings;
    }

    public void saveLastId(int last_id) throws IOException {
        FileOutputStream output = new FileOutputStream(String.format("%s/lastId.txt", JSON_FOLDER));
        output.write(String.valueOf(last_id).getBytes());
        output.close();
    }

    public Integer loadLastId() {
        if (!new File(JSON_FOLDER).exists()) {
            return null;
        }
        Integer id;
        try{
            FileInputStream input = new FileInputStream(String.format("%s/lastId.txt", JSON_FOLDER));
            byte[] bytes = input.readAllBytes();
            id = Integer.parseInt(new String(bytes));
            input.close();

        }catch (IOException e){
            id = null;
        }
        return id;
    }

    public void buildWiseSayings(List<WiseSaying> wiseSayings)  throws IOException{
        StringBuilder json = new StringBuilder();
        json.append("[\n");
        int wiseSayingIndex = 0;
        for (WiseSaying ws = wiseSayings.getFirst(); wiseSayingIndex<wiseSayings.size(); ws = wiseSayings.get(wiseSayingIndex++) ) {
            String[] lines = wiseSaying2JsonString(ws).split("\n");
            for (int line_index = 0; line_index< lines.length; line_index++) {
                String line = lines[line_index];
                json.append("    ");
                json.append(line);
                if (line_index == lines.length-1 && wiseSayingIndex == wiseSayings.size()-2) {
                    json.append(",");
                }
                json.append("\n");

            }
        }
        json.append("]");

        FileOutputStream output = new FileOutputStream(String.format("%s/data.json", JSON_FOLDER));
        output.write(json.toString().getBytes());
        output.close();
    }

}
