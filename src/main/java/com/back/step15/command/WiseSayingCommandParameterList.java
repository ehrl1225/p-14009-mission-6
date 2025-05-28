package com.back.step15.command;

public class WiseSayingCommandParameterList {
    private final int DEFAULT_CAPACITY = 4;
    private WiseSayingCommandParameter[] parameters;
    private int capacity;
    private int size;

    WiseSayingCommandParameterList(){
        parameters = new WiseSayingCommandParameter[DEFAULT_CAPACITY];
        capacity = DEFAULT_CAPACITY;
        size = 0;
    }

    public int getSize(){
        return size;
    }

    private Boolean isFull(){
        return size >= capacity;
    }

    private void sizeUp(){
        WiseSayingCommandParameter[] temp = parameters;
        capacity = capacity << 1;
        parameters = new WiseSayingCommandParameter[capacity];
        System.arraycopy(temp, 0, parameters, 0, temp.length);
    }

    public void add(WiseSayingCommandParameter parameter){
        if (isFull()){
            sizeUp();
        }
        parameters[size++] = parameter;
    }

    public WiseSayingCommandParameter getByName(WiseSayingCommandParameterToken parameterName){
        for(int i = 0; i < size; i++){
            WiseSayingCommandParameter parameter = parameters[i];
            if (parameter.getName() == parameterName){
                return parameter;
            }
        }
        return null;
    }

    public boolean hasParameter(WiseSayingCommandParameterToken parameterName){
        WiseSayingCommandParameter parameter = getByName(parameterName);
        return parameter != null;
    }

    static WiseSayingCommandParameterList emptyList(){
        return new WiseSayingCommandParameterList();
    }
}
