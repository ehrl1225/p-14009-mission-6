package com.back.step15;

public class WiseSaying {
    public static final int NULL_ID = 0;
    private int id;
    private String content;
    private String author;

    public WiseSaying(){
        id = NULL_ID;
    }

    public WiseSaying(String content, String author){
        this.content = content;
        this.author = author;
    }

    // getters

    public int getId(){
        return id;
    }

    public String getContent(){
        return content;
    }

    public String getAuthor(){
        return author;
    }

    //setter

    public void setID(int id){
        this.id = id;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public void setData(WiseSaying saying){
        content = saying.getContent();
        author = saying.getAuthor();
    }
}
