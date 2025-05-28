package com.back.step15.command;

public class WiseSayingCommandParameter {
    private WiseSayingCommandParameterToken name;
    private String value;
    private Boolean isNumeric;

    private void checkValueNumeric(){
        isNumeric = true;
        for (int i =0; i < value.length(); i++){
            char c = value.charAt(i);
            if (!Character.isDigit(c)){
                isNumeric = false;
            }
        }
    }

    public WiseSayingCommandParameter(WiseSayingCommandParameterToken name, String value) {
        this.name = name;
        this.value = value;
        checkValueNumeric();
    }

    public WiseSayingCommandParameterToken getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public int getIntValue() {
        if (isNumeric){
            return Integer.parseInt(value);
        }
        throw new RuntimeException("WiseSayingCommandParameter is not numeric");
    }
}
