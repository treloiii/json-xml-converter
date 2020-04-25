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
//        sb.append("\n   ");
        for (Map.Entry<String,Object> entry:values.entrySet()) {
            sb.append("<").append(entry.getKey()).append(">");

            if(entry.getValue() instanceof String)
                sb.append(entry.getValue());
            else {
                sb.append("\n   ");
                sb.append(entry.getValue().toString());
            }
//            sb.append("\n   ");
            sb.append("</").append(entry.getKey()).append(">");
            sb.append("\n   ");
        }
        for(Map.Entry<String,List<Object>> entry:arrays.entrySet()){
            entry.getValue().forEach(o->{
                sb.append("<").append(entry.getKey()).append(">");
                if(o instanceof String) {
                    sb.append(o);
                }
                else {
                    sb.append("\n       ").append(o.toString()).append("\n  ");
                }
                sb.append("</").append(entry.getKey()).append(">").append("\n   ");
            });
        }
//        if(sb.charAt(sb.length()-3)==']')
//            sb.deleteCharAt(sb.lastIndexOf(","));
//        int last=sb.lastIndexOf(",");
//        if(last!=-1)
//            sb.deleteCharAt(last);
//        sb.append("}");
        return sb.toString();
    }
}
