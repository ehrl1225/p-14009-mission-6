package com.back.step15;

import com.back.step15.command.WiseSayingCommand;
import com.back.step15.command.WiseSayingCommandParameterToken;
import com.back.step15.repository.WiseSayingView;

import java.util.Scanner;

public class WiseSayingController {
    WiseSayingService service;
    Scanner scanner;

    WiseSayingController(Scanner sc) {
        service = new WiseSayingService();
        scanner = sc;
    }

    public void close(){
        scanner.close();
        service.close();
    }

    private String banSpecialLetters(String str){
        return str.replaceAll("[@?]", "");
    }

    private String getInput(){
        String input_str = scanner.nextLine().trim();
        return banSpecialLetters(input_str);
    }

    private void insert(){
        System.out.print("명언 : ");
        String content = getInput();
        System.out.print("작가 : ");
        String author = getInput();
        int id = service.insertWiseSaying(content, author);
        System.out.println(id + "번 명언이 등록되었습니다.");
    }

    private void show(WiseSayingCommand cmd){
        int page = 1;
        if (cmd.hasParameter(WiseSayingCommandParameterToken.Page)){
            page = cmd.getIntParameter(WiseSayingCommandParameterToken.Page);
        }
        if (cmd.hasParameter(WiseSayingCommandParameterToken.KeyWordType) && cmd.hasParameter(WiseSayingCommandParameterToken.Keyword)){
            String keywordType = cmd.getStringParameter(WiseSayingCommandParameterToken.KeyWordType);
            String keyword = cmd.getStringParameter(WiseSayingCommandParameterToken.Keyword);

            WiseSayingView wiseSayingList = service.searchWiseSaying(keywordType, keyword, page);
            System.out.println("----------------------");
            System.out.println("검색타입 : " + keywordType);
            System.out.println("검색어 : " + keyword);
            System.out.println("----------------------");
            System.out.print(wiseSayingList);
            return;
        }
        System.out.print(service.showWiseSayings(page));
    }

    private void delete(WiseSayingCommand wiseSayingCommand){
        try{
            int id = wiseSayingCommand.getIntParameter(WiseSayingCommandParameterToken.Id);
            if (!service.hasWiseSaying(id)){
                System.out.println( id+"번 명언은 존재하지 않습니다.");
                return;
            }
            service.deleteWiseSaying(id);
            System.out.println(id + "번 명언이 삭제되었습니다.");

        }catch (RuntimeException e){
            System.out.println("ID를 입력하시오.");
        }
    }

    private void update(WiseSayingCommand wiseSayingCommand){
        try{
            int id = wiseSayingCommand.getIntParameter(WiseSayingCommandParameterToken.Id);
            WiseSaying wiseSaying = service.getWiseSaying(id);
            if (wiseSaying == null){
                System.out.println( id+"번 명언은 존재하지 않습니다.");
                return;
            }
            System.out.println("명언(기존) : " + wiseSaying.getContent());
            System.out.print("명언 : ");
            String content = getInput();
            System.out.println("작가(기존) : " + wiseSaying.getAuthor());
            System.out.print("작가 : ");
            String author = getInput();
            service.updateWiseSaying(id,content, author);

        }catch (RuntimeException e){
            System.out.println("ID를 입력하시오.");
        }
    }

    private void build(){
        service.buildWiseSayingList();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }

    public void work(WiseSayingCommand cmd){
        switch (cmd.getMainCommandToken()){
            case Insert -> insert();
            case Show -> show(cmd);
            case Delete -> delete(cmd);
            case Update -> update(cmd);
            case Build -> build();
        }
    }
}
