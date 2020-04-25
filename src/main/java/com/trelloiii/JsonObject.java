package com.trelloiii;

import java.util.*;

public class JsonObject extends AbstractParsingObject {
    public JsonObject() {
        super();
    }
    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("{");
        for (Map.Entry<String,Object> entry:values.entrySet()) {
            sb.append("\"").append(entry.getKey()).append("\"");
            sb.append(":");
            if(entry.getValue() instanceof String)
                sb.append("\"").append(entry.getValue()).append("\"");
            else
                sb.append(entry.getValue().toString());
            sb.append(",");
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
