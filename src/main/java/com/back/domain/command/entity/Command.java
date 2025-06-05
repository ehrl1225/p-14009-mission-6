package com.back.domain.command.entity;

import lombok.Getter;

import java.util.List;

@Getter
public class Command {
    private final String command;
    private final List<CommandParameter> parameters;


    public Command(String command, List<CommandParameter> parameters) {
        this.command = command;
        this.parameters = parameters;
    }

    public CommandParameter getParameter(String key){
        if (parameters==null) return null;
        for (CommandParameter parameter : parameters) {
            if (parameter.getKey().equals(key)){
                return parameter;
            }
        }
        return null;
    }

    public String getParameterAsString(String key){
        CommandParameter parameter = getParameter(key);
        if (parameter==null) return null;
        return parameter.getValue();
    }

    public Integer getParameterAsInteger(String key){
        CommandParameter parameter = getParameter(key);
        if (parameter==null) return null;
        try{
            return Integer.parseInt(parameter.getValue());
        }catch (NumberFormatException e){
            return null;
        }

    }
}
