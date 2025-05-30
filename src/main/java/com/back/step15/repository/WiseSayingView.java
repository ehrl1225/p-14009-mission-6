package com.back.step15.repository;

import com.back.step15.WiseSaying;

public class WiseSayingView {
    private final static int PAGE_SIZE = 5;
    private int page_number;
    private int max_page;
    private final WiseSaying[] wise_sayings;
    private int size;

    private void loadPage(WiseSayingList.WiseSayingIterator iterator) {
        int index = 0;
        while (iterator.hasNext()) {
            WiseSaying wise_saying = iterator.next();
            wise_sayings[index] = wise_saying;
            index++;
        }
        size = index;
        return;
    }

    public WiseSayingView(WiseSayingList.WiseSayingIterator iterator, int page_number, int max_page) {
        this.page_number = page_number;
        this.max_page = max_page;
        wise_sayings = new WiseSaying[PAGE_SIZE];
        loadPage(iterator);
    }

    private WiseSayingView() {
        wise_sayings = new WiseSaying[PAGE_SIZE];
    }

    public void add(WiseSaying wise_saying) {
        if (size >= PAGE_SIZE){
            return;
        }
        wise_sayings[size++] = wise_saying;
    }

    public static WiseSayingView getEmptyView() {
        return new WiseSayingView();
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("번호 / 작가 / 명언\n");
        builder.append("----------------------\n");
        for (int i = 0; i<size; i++){
            builder.append(wise_sayings[i].getId());
            builder.append(" / ");
            builder.append(wise_sayings[i].getAuthor());
            builder.append(" / ");
            builder.append(wise_sayings[i].getContent());
            builder.append("\n");
        }
        builder.append("----------------------\n");
        builder.append("페이지 : ");
        for (int i = 1; i< max_page; i++){
            if (i == page_number){
                builder.append("[");
                builder.append(page_number);
                builder.append("] / ");
                continue;
            }
            builder.append(i);
            builder.append(" / ");
        }
        if (max_page == page_number){
            builder.append("[");
            builder.append(page_number);
            builder.append("]\n");
        }else{
            builder.append(max_page);
            builder.append("\n");
        }
        return builder.toString();
    }

    public void setPage(int page) {
        this.page_number = page;
    }

    public void setMaxPage(int maxPage) {
        max_page = maxPage;
    }
}
