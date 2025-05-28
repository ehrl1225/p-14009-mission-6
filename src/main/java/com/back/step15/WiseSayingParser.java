package com.back.step15;

public class WiseSayingParser {
    private String input;
    private int index;
    public WiseSayingParser(){}

    public void setString(String s){
        input = s;
        index = 0;
    }

    public Boolean findChar(char ch){
        for (int i = index; i<input.length(); i++){
            char str_ch = input.charAt(i);
            if (str_ch == ch){
                index = i;
                return true;
            }
        }
        return false;
    }

    public Boolean hasChar(char ch){
        for (int i = index; i<input.length(); i++){
            char str_ch = input.charAt(i);
            if (str_ch == ch){
                return true;
            }
        }
        return false;
    }

    public void consumeChar(char ch){
        char str_ch = input.charAt(index);
        if (str_ch == ch){
            index++;
        }
    }

    public void findNumber(){
        for (int i = index; i<input.length(); i++){
            char ch = input.charAt(i);
            if ('0' <= ch && ch <= '9'){
                index = i;
                return;
            }
        }
    }

    public void findString(){
        findChar('\"');
    }

    public String readString(){
        consumeChar('\"');
        int start = index;
        findChar('\"');
        int end = index;
        return input.substring(start, end);
    }

    public int readNumber(){
        int start = index;
        int end = index;
        for (int i = index; i<input.length(); i++){
            char ch = input.charAt(i);
            if ('0' > ch || ch > '9'){
                end = i;
                break;
            }
        }
        String num_str = input.substring(start, end);
        if (num_str.isEmpty()){
            return -1;
        }
        return Integer.parseInt(num_str);
    }

    public String getStringUntilChar(char ch){
        int start = index;
        findChar(ch);
        int end = index;
        return input.substring(start, end);
    }

    public String getStringFromChar(char ch){
        if (!findChar(ch)){
            return "";
        }
        consumeChar(ch);
        int start = index;
        return input.substring(start);
    }
}
