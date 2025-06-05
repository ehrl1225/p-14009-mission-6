package com.back.domain.wiseSaying.controller;

import com.back.domain.command.entity.Command;
import com.back.domain.command.entity.CommandParameter;
import com.back.domain.wiseSaying.dto.Page;
import com.back.domain.wiseSaying.dto.Pageable;
import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.service.WiseSayingService;
import lombok.Setter;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class WiseSayingController {
    @Setter
    private Scanner scanner;
    private final WiseSayingService wiseSayingService;


    public WiseSayingController(WiseSayingService wiseSayingService) {
        this.wiseSayingService = wiseSayingService;
    }

    private void printWiseSayings(Page<WiseSaying> page) {
        List<WiseSaying> wiseSayings = page.getContent();
        int max_page = (page.getTotalCount()-1)/page.getPageSize() + 1;
        int page_no = page.getPage();
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        for (WiseSaying wiseSaying : wiseSayings.reversed()) {
            System.out.printf("%d / %s / %s\n", wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent());
        }
        System.out.print("페이지 : ");
        for (int i = 1; i <= max_page;i++){
            if (page_no == i){
                System.out.printf("[%d]", i);
            }else{
                System.out.print(i);
            }
            if (i < max_page){
                System.out.print(" / ");
            }
        }
        System.out.println();
    }

    private Integer getId(Command command) {
        CommandParameter id_param = command.getParameter("id");
        if (id_param == null) {
            System.out.println("ID 값을 찾지 못했습니다.");
            return null;
        }
        int id;
        try{
            id = Integer.parseInt(id_param.getValue());
        }catch (NumberFormatException e){
            System.out.println("ID가 숫자가 아닙니다.");
            return null;
        }
        return id;
    }

    private void insert(){
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();
        try{
            int id = wiseSayingService.insert(content, author);
            System.out.printf("%d번 명언이 등록되었습니다.\n", id);
        }catch (IOException e){
            System.out.println("저장 중 오류가 발생했습니다.");
        }
    }

    private void select(Command command) {
        String keyword = command.getParameterAsString("keyword");
        String keywordType = command.getParameterAsString("keywordType");
        Integer page = command.getParameterAsInteger("page");
        Integer pageSize = command.getParameterAsInteger("pageSize");
        if (page==null){
            page=1;
        }
        if (pageSize==null){
            pageSize=5;
        }
        Pageable pageable = new Pageable(page, pageSize);
        if (keyword != null) {
            if (keywordType == null){
                keywordType = "All";
            }
            System.out.println("----------------------");
            System.out.printf("검색타입 : %s\n", keywordType);
            System.out.printf("검색어 : %s\n", keyword);
            System.out.println("----------------------");

        }
        Page<WiseSaying> result = wiseSayingService.selectPage(keywordType, keyword, pageable);
        printWiseSayings(result);
    }

    private void delete(Command command){
        Integer id = getId(command);
        if (id == null){
            return;
        }
        if (!wiseSayingService.delete(id)) {
            System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
            return;
        }
        System.out.printf("%d번 명언이 삭제되었습니다.\n", id);
    }

    private void update(Command command){
        Integer id = getId(command);
        if (id == null) {
            return;
        }
        WiseSaying wiseSaying = wiseSayingService.selectById(id);
        if (wiseSaying == null) {
            System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
            return;
        }
        System.out.printf("명언(기존) : %s\n", wiseSaying.getContent());
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();
        System.out.printf("작가(기존) : %s\n", wiseSaying.getAuthor());
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();
        try{
            wiseSayingService.update(id, content, author);
        }catch (IOException e){
            System.out.println("저장 중 오류가 발생했습니다.");
        }
    }

    private void build(){
        try{
            wiseSayingService.build();
        }catch (IOException e){
            System.out.println("저장 중 오류가 발생했습니다.");
        }
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }

    public void work(Command command) {
        switch (command.getCommand()){
            case "등록" -> insert();
            case "목록" -> select(command);
            case "삭제" -> delete(command);
            case "수정" -> update(command);
            case "빌드" -> build();
        }
    }
}
