package com.back;

import com.back.domain.command.entity.Command;


import java.io.IOException;
import java.util.Scanner;

public class App {

    public void run(){
        System.out.println("== 명언 앱 ==");
        Scanner sc = new Scanner(System.in);
        AppContext appContext = new AppContext(sc);
        while(true){
            System.out.print("명령) ");
            String input = appContext.scanner.nextLine().trim();
            Command command = appContext.commandController.parseCommand(input);
            if (command == null){
                continue;
            }
            appContext.systemController.work(command);
            if (appContext.systemController.work(command)){
                break;
            }
            appContext.wiseSayingController.work(command);
        }
        try{
            appContext.close();

        }catch (IOException e){
            System.out.println("저장 중 오류가 발생했습니다.");
        }

    }
}
