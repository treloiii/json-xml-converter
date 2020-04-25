package com.trelloiii;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonToXmlParser extends AbstractParser {

    public JsonToXmlParser(String filePath) {
        super(filePath);
    }
    @Override
    public void parse() {
        try {
            FileReader fileReader = new FileReader(this.fileToParse);
            JsonReader reader = new JsonReader(fileReader);
            JsonElement jsonElement = JsonParser.parseReader(reader);
            XmlObject object = new XmlObject();
            deepParse(jsonElement, object);
            String xml=validXml(object.toString());
            generateOutputFile(xml);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private String validXml(String o){
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                "\n" +
                o;
    }
    private void deepParse(JsonElement element, XmlObject object){
        for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
            JsonElement jsonElement=entry.getValue();
            if(jsonElement.isJsonObject()) {
                XmlObject object1=new XmlObject();
                System.out.println("object : "+entry.getKey());
                deepParse(entry.getValue(), object1);
                object.add(object1,entry.getKey());
            }
            else if(jsonElement.isJsonArray()){
                System.out.println("array : "+entry.getKey());
//                    XmlObject object1=new XmlObject();
                JsonArray array=normalize(jsonElement.getAsJsonArray());
                List<Object> arr=new ArrayList<>();
                for(JsonElement el:array){
                    if(el.isJsonPrimitive())
                        arr.add(el.getAsJsonPrimitive().toString());
                    else {
                        XmlObject object2=new XmlObject();
                        arr.add(object2);
                        deepParse(el, object2);
                    }
                }
                object.addArray(arr,entry.getKey());
            }
            else if(jsonElement.isJsonPrimitive()){
                System.out.println("primitive : "+entry.getKey());
//                    XmlObject object1=new XmlObject();
                object.add(jsonElement.getAsJsonPrimitive().toString(),entry.getKey());
            }
//            }
//            System.out.println(object);
        }
//        else if(element.isJsonArray()){
//            JsonArray array=normalize(element.getAsJsonArray());
//            for(JsonElement jsonElement:array){
//                deepParse(jsonElement,object);
//            }
//        }
//        else if(element.isJsonPrimitive()){
//            object.add(element.getAsJsonPrimitive().toString(),"__text");
//        }
    }

    private JsonArray normalize(JsonArray jsonArray){
        for(int index=0;index<jsonArray.size();index++){
            JsonElement element=jsonArray.get(index);
            if(element.isJsonArray()){// если элемент массив
                JsonArray normalized=normalize(element.getAsJsonArray()); // нормализуем массив
                JsonArray bufEnd=new JsonArray();//делим исходный массив пополам
                JsonArray bufStart=new JsonArray();
                for(int i=0;i<jsonArray.size();i++){
                    if(i<index)
                        bufStart.add(jsonArray.get(i));
                    else if(i>index)
                        bufEnd.add(jsonArray.get(i));
                }
                index=index+normalized.size()-1;//добавляем к началу нормаль и к нормали конец
                bufStart.addAll(normalized);
                bufStart.addAll(bufEnd);
                jsonArray=bufStart;
            }
        }
        return jsonArray;
    }
}
