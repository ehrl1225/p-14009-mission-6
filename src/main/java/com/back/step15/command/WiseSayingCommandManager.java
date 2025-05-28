package com.back.step15.command;

import com.back.step15.WiseSayingParser;

public class WiseSayingCommandManager extends WiseSayingParser {

    public WiseSayingCommandManager(){

    }

    private WiseSayingCommandParameterList getCommandParameterList(){
        WiseSayingCommandParameterList list = new WiseSayingCommandParameterList();
        boolean run = true;
        while(run){
            String name = getStringUntilChar('=');
            String value;
            if (hasChar('&')){
                consumeChar('=');
                value = getStringUntilChar('&');
                consumeChar('&');
            }else{
                value = getStringFromChar('=');
                run = false;
            }
            WiseSayingCommandParameterToken parameterToken = switch(name){
                case "id" -> WiseSayingCommandParameterToken.Id;
                case "keywordType" -> WiseSayingCommandParameterToken.KeyWordType;
                case "keyword" -> WiseSayingCommandParameterToken.Keyword;
                case "page" -> WiseSayingCommandParameterToken.Page;
                default -> WiseSayingCommandParameterToken.NULL;
            };
            if (parameterToken != WiseSayingCommandParameterToken.NULL){
                WiseSayingCommandParameter param = new WiseSayingCommandParameter(parameterToken, value);
                list.add(param);
            }
        }
        return list;
    }

    public WiseSayingCommand getWiseSayingCommand(String str){
        setString(str);
        String main_command;
        WiseSayingCommandParameterList params;
        if (hasChar('?')){
            main_command = getStringUntilChar('?');
            consumeChar('?');
             params = getCommandParameterList();
        }else{
            main_command = str;
            params = WiseSayingCommandParameterList.emptyList();
        }

        WiseSayingCommandToken main_command_token = switch (main_command) {
            case "종료" -> WiseSayingCommandToken.Terminate;
            case "등록" -> WiseSayingCommandToken.Insert;
            case "목록" -> WiseSayingCommandToken.Show;
            case "삭제" -> WiseSayingCommandToken.Delete;
            case "수정" -> WiseSayingCommandToken.Update;
            case "빌드" -> WiseSayingCommandToken.Build;
            default -> WiseSayingCommandToken.NULL;
        };

        return new WiseSayingCommand(main_command_token, params);
    }
}
