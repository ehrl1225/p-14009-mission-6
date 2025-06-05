package com.back.domain.command.service;

import com.back.domain.command.entity.Command;
import com.back.domain.command.entity.CommandParameter;

import java.util.ArrayList;
import java.util.List;

public class CommandService {

    public CommandService() {

    }

    private List<CommandParameter> getCommandParameters(String parametersString) {
        String[] parameters = parametersString.split("&");
        if (parameters.length == 0) {
            return null;
        }
        List<CommandParameter> commandParameters = new ArrayList<>();
        for (String parameter : parameters) {
            String[] parameter_str = parameter.split("=");
            if (parameter_str.length != 2) {
                continue;
            }
            String key = parameter_str[0];
            String value = parameter_str[1];
            commandParameters.add( new CommandParameter(key, value));
        }
        return commandParameters;
    }

    public Command getCommand(String commandString) {
        String[] command_str = commandString.split("\\?");
        List<CommandParameter> commandParameters;
        switch (command_str.length) {
            case 0:
                return null;
            case 1:
                commandParameters = null;
                break;
            case 2:
                commandParameters = getCommandParameters(command_str[1]);
                break;
            default:
                commandParameters = null;
        }
        String command = command_str[0];

        return new Command(command, commandParameters);


    }
}
