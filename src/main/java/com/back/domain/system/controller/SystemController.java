package com.back.domain.system.controller;

import com.back.domain.command.entity.Command;

public class SystemController {

    public SystemController(){

    }

    public boolean work(Command command){
        return command.getCommand().equals("종료");
    }
}
