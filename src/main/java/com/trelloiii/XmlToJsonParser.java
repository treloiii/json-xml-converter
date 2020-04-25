package com.trelloiii;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sun.org.apache.xerces.internal.dom.ElementImpl;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

public class XmlToJsonParser extends AbstractParser {
    private File tempFile;
    public XmlToJsonParser(String filePath) {
        super(filePath);
    }
    private void optimizeCopy() throws IOException {
        FileReader fileReader=new FileReader(fileToParse);
        BufferedReader reader=new BufferedReader(fileReader);
        String line;
        StringBuilder s=new StringBuilder();
        while ((line=reader.readLine())!=null){
            line=line.trim();
            s.append(line);
        }
        reader.close();
        fileReader.close();
        tempFile = new File(fileToParse.getParentFile() + "/temp.xml");
        FileWriter writer=new FileWriter(tempFile);
        BufferedWriter bufferedWriter=new BufferedWriter(writer);
        bufferedWriter.write(s.toString());
        bufferedWriter.close();
        writer.close();
    }

    @Override
    public void parse() {
        try {
            optimizeCopy();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(tempFile);
            Element parent = document.getDocumentElement();
            JsonObject jsonObject=new JsonObject();
            deepParse(parent,jsonObject);
            Gson gson=new GsonBuilder().setPrettyPrinting().create();
            JsonElement json=JsonParser.parseString(jsonObject.toString());
            generateOutputFile(gson.toJson(json));
            deleteTempFile(tempFile);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void deepParse(Element parent,JsonObject jsonObject) throws IOException {
        if(hasChildNodes(parent)) {
            JsonObject jsonObject1=new JsonObject();
            if(parent.hasAttributes()){
                NamedNodeMap nodeMap=parent.getAttributes();
                for(int i=0;i< nodeMap.getLength();i++){
                    Node attribute=nodeMap.item(i);
                    jsonObject1.add(attribute.getTextContent(),"_"+attribute.getNodeName());
                }
            }
            jsonObject.add(jsonObject1,parent.getTagName());
            NodeList childNodes = parent.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node child=childNodes.item(i);
                if(child.getNodeType()==Node.ELEMENT_NODE)
                    deepParse((Element)child,jsonObject1);
                else if(child.getNodeType()==Node.TEXT_NODE)
                    jsonObject1.add(child.getTextContent(),"__text");
            }
        }
        else{
            if(parent.hasAttributes()){
                JsonObject jsonObject1=new JsonObject();
                NamedNodeMap nodeMap=parent.getAttributes();
                for(int i=0;i< nodeMap.getLength();i++){
                    Node attribute=nodeMap.item(i);
                    jsonObject1.add(attribute.getTextContent(),"_"+attribute.getNodeName());
                }
                jsonObject1.add(parent.getTextContent(),"__text");
                jsonObject.add(jsonObject1,parent.getTagName());
            }
            else {
                jsonObject.add(parent.getTextContent(), parent.getTagName());
            }
        }
    }
    private boolean hasChildNodes(Node node){
        if(node.hasChildNodes()){
            NodeList list=node.getChildNodes();
            if(list.getLength()>1){
                return true;
            }
            else {
                return !(list.item(0).getNodeType()==Node.TEXT_NODE);
            }
        }
        else{
            return false;
        }
    }
}
