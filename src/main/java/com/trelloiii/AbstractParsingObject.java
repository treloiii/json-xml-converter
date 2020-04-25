package com.trelloiii;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractParsingObject {
    protected Map<String,Object> values;
    protected Map<String, List<Object>> arrays;

    public AbstractParsingObject() {
        values=new LinkedHashMap<>();
        arrays=new LinkedHashMap<>();
    }

    public void addArray(List<Object> array,String key){
        arrays.put(key,array);
    }
    public void add(Object o, String key){
        if(arrays.containsKey(key)){
            List<Object> list=arrays.get(key);
            list.add(o);
            return;
        }
        if(values.containsKey(key)){
            Object removed=values.remove(key);
            List<Object> newList=new ArrayList<>();
            newList.add(removed);
            newList.add(o);
            arrays.put(key,newList);
            return;
        }
        values.put(key,o);
    }
    @Override
    public abstract String toString();
}
