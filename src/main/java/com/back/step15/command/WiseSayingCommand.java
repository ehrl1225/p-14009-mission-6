package com.back.step15.command;

public class WiseSayingCommand {
    private WiseSayingCommandToken main_command_token;
    private WiseSayingCommandParameterList parameter_list;

    WiseSayingCommand(WiseSayingCommandToken main_command_token, WiseSayingCommandParameterList parameter_list) {
        this.main_command_token = main_command_token;
        this.parameter_list = parameter_list;
    }

    public WiseSayingCommandToken getMainCommandToken() {
        return main_command_token;
    }

    public WiseSayingCommandParameterList getParameterList() {
        return parameter_list;
    }

    public boolean hasParameter(WiseSayingCommandParameterToken parameter_name) {
        return parameter_list.hasParameter(parameter_name);
    }

    public int getParameterCount() {
        return parameter_list.getSize();
    }

    public int getIntParameter(WiseSayingCommandParameterToken parameter_name) throws RuntimeException {
        WiseSayingCommandParameter parameter = parameter_list.getByName(parameter_name);
        if (parameter == null) {
            throw new RuntimeException("No such parameter: " + parameter_name);
        }
        return parameter.getIntValue();
    }

    public String getStringParameter(WiseSayingCommandParameterToken parameter_name) throws RuntimeException {
        WiseSayingCommandParameter parameter = parameter_list.getByName(parameter_name);
        if (parameter == null) {
            throw new RuntimeException("No such parameter: " + parameter_name);
        }
        return parameter.getValue();
    }

}
