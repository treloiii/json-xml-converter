package com.trelloiii;

import java.util.List;
import java.util.Map;

public class XmlObject extends AbstractParsingObject {
    public XmlObject() {
        super();
    }
    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        for (Map.Entry<String,Object> entry:values.entrySet()) {
            sb.append("<").append(entry.getKey()).append(">");
            if(entry.getValue() instanceof String)
                sb.append(entry.getValue());
            else {
                sb.append(entry.getValue().toString());
            }
            sb.append("</").append(entry.getKey()).append(">");
        }
        for(Map.Entry<String,List<Object>> entry:arrays.entrySet()){
            entry.getValue().forEach(o->{
                sb.append("<").append(entry.getKey()).append(">");
                if(o instanceof String) {
                    sb.append(o);
                }
                else {
                    sb.append(o.toString());
                }
                sb.append("</").append(entry.getKey()).append(">");
            });
        }
        return sb.toString();
    }
}
