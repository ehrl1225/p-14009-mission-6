package com.back.domain.command.controller;
import com.back.domain.command.entity.Command;
import com.back.domain.command.service.CommandService;

public class CommandController {
    private final CommandService commandService;


    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    public Command parseCommand(String commandString){
        return commandService.getCommand(commandString);
    }

}
