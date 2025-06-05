package com.back;

import com.back.domain.command.controller.CommandController;
import com.back.domain.command.service.CommandService;
import com.back.domain.system.controller.SystemController;
import com.back.domain.wiseSaying.controller.WiseSayingController;
import com.back.domain.wiseSaying.repository.WiseSayingRepository;
import com.back.domain.wiseSaying.service.WiseSayingService;

import java.io.IOException;
import java.util.Scanner;

public class AppContext {
    public CommandController commandController;
    public SystemController systemController;
    public WiseSayingController wiseSayingController;
    public CommandService commandService;
    public WiseSayingService wiseSayingService;
    public WiseSayingRepository wiseSayingRepository;
    public Scanner scanner;

    private void initRepository()  {
        wiseSayingRepository = new WiseSayingRepository();

    }

    private void initService(){
        commandService = new CommandService();
        wiseSayingService = new WiseSayingService(wiseSayingRepository);
    }

    private void initController(){
        commandController = new CommandController(commandService);
        systemController = new SystemController();
        wiseSayingController = new WiseSayingController(wiseSayingService);

        wiseSayingController.setScanner(scanner);
    }

    private void closeRepository(){
        wiseSayingRepository.close();
    }

    public void close() throws  IOException{
        closeRepository();
        scanner.close();
    }

    public void init() {
        initRepository();
        initService();
        initController();
    }

    public AppContext(Scanner scanner){
        this.scanner = scanner;
        init();
    }

}
