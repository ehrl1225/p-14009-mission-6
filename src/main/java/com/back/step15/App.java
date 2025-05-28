package com.back.step15;

import com.back.step15.command.WiseSayingCommand;
import com.back.step15.command.WiseSayingCommandManager;
import com.back.step15.command.WiseSayingCommandToken;

import java.util.Scanner;

public class App {
    private final WiseSayingCommandManager wsm;
    private final WiseSayingController wiseSayingController;
    Scanner sc = new Scanner(System.in);

    App(){
        wsm = new WiseSayingCommandManager();
        wiseSayingController = new WiseSayingController(sc);
    }

    public void run(){
        System.out.println("== 명언 앱 ==");

        while(true){
            System.out.print("명령) ");
            String input = sc.nextLine().trim();
            WiseSayingCommand command = wsm.getWiseSayingCommand(input);
            WiseSayingCommandToken commandToken = command.getMainCommandToken();
            if (commandToken== WiseSayingCommandToken.Terminate){
                break;
            }
            if (commandToken != WiseSayingCommandToken.NULL){
                wiseSayingController.work(command);
            }
        }
        sc.close();
        wiseSayingController.close();
    }
}
