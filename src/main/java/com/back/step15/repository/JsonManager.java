package com.back.step15.repository;


import com.back.step15.WiseSaying;
import com.back.step15.WiseSayingParser;

public class JsonManager extends WiseSayingParser {


    JsonManager() {}

    /**
     * makes one WiseSaying to json String
     * @param wiseSaying
     * @return
     */
    public String WiseSaying2json(WiseSaying wiseSaying) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"id\": ");
        json.append(wiseSaying.getId());
        json.append(",\n");
        json.append("  \"content\": \"");
        json.append(wiseSaying.getContent());
        json.append("\",\n");
        json.append("  \"author\": \"");
        json.append(wiseSaying.getAuthor());
        json.append("\"\n}");
        return json.toString();
    }

    /**
     * makes many WiseSayings to json string
     * @param iterator
     * @return
     */
    public String wiseSaying2json(WiseSayingList.WiseSayingIterator iterator){
        StringBuilder json = new StringBuilder();
        json.append("[\n");
        while(iterator.hasNext()){
            // makes indent spaces
            String content_json = WiseSaying2json(iterator.next());
            String[] lines = content_json.split("\n");
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                json.append("  ");
                json.append(line);
                // last one need "," before "\n"
                if (i < lines.length - 1) {
                    json.append("\n");
                }
            }
            if (iterator.hasNext()) {
                json.append(",\n");
            }
        }
        json.append("\n]");
        return json.toString();
    }
}
