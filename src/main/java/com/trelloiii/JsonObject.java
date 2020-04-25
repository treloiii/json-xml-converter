package com.trelloiii;

import java.util.*;

public class JsonObject {
    private Map<String,Object> values;
    private Map<String,List<Object>> arrays;

    public JsonObject() {
        values=new LinkedHashMap<>();
        arrays=new LinkedHashMap<>();
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
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("{");
        sb.append("\n");
        for (Map.Entry<String,Object> entry:values.entrySet()) {
            sb.append("\"").append(entry.getKey()).append("\"");
            sb.append(":");
            if(entry.getValue() instanceof String)
                sb.append("\"").append(entry.getValue()).append("\"");
            else
                sb.append(entry.getValue().toString());
            sb.append(",");
            sb.append("\n");
        }
        for(Map.Entry<String,List<Object>> entry:arrays.entrySet()){
            sb.append("\"").append(entry.getKey()).append("\"");
            sb.append(":").append("[");
            entry.getValue().forEach(o->{
                if(o instanceof String)
                    sb.append("\"").append(o).append("\"").append(",");
                else
                    sb.append(o.toString()).append(",");
            });
            sb.append("]");
            sb.append("\n");
            sb.append(",");
        }
        if(sb.charAt(sb.length()-3)==']')
            sb.deleteCharAt(sb.lastIndexOf(","));
        int last=sb.lastIndexOf(",");
        if(last!=-1)
            sb.deleteCharAt(last);
        sb.append("}");
        return sb.toString();
    }
}
