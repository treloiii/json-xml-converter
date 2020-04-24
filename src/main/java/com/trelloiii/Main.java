package com.trelloiii;

import com.google.gson.Gson;
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
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Parser parser=new XmlToJsonParser("src/main/resources/parser.xml");
        parser.parse();



//        DocumentBuilderFactory builderFactory=DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder=builderFactory.newDocumentBuilder();
//        Document document=builder.parse(new File("src/main/resources/parserBuf.xml"));
//        Element parent=document.getDocumentElement();
//        JsonObject jsonObject=new JsonObject();
//        deepParse(parent,jsonObject);
//        System.out.println(jsonObject);

//        Gson gson=new Gson();
//        try(JsonWriter writer=new JsonWriter(new FileWriter(new File("src/main/resources/parser0.json")))) {
//
//            writer.beginObject();
//            writer.name("test");
//            writer.jsonValue("sd");
//            writer.endObject();
//        }
//        JsonObject jsonObject=new JsonObject();
//        jsonObject.add("value1","key1");
//        jsonObject.add("value2","key2");
//        jsonObject.add("value3","key3");
//        jsonObject.add("value3","key3");
//        JsonObject jsonObject1=new JsonObject();
//        jsonObject1.add("val1","k1");
//        jsonObject1.add("val2","k2");
//        jsonObject1.add("val2","k2");
//        jsonObject.add(jsonObject1,"object");
//        System.out.println(jsonObject);
    }

}
