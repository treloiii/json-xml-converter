package com.trelloiii;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
//        Parser parser=new XmlToJsonParser("src/main/resources/parser.xml");
//        parser.parse();

//        FileReader fileReader=new FileReader("src/main/resources/parser.json");
//        JsonReader reader=new JsonReader(fileReader);
//        JsonElement jsonElement=JsonParser.parseReader(reader);
//        XmlObject object=new XmlObject();
//        deepParse(jsonElement,object);
//        System.out.println(object);
        Parser parser=new JsonToXmlParser("src/main/resources/parser.json");
        parser.parse();

    }
    public static void deepParse(JsonElement element,XmlObject object){
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

    public static JsonArray normalize(JsonArray jsonArray){
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
