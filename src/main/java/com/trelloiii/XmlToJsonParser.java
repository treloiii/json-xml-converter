package com.trelloiii;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
            generateOutputFile(jsonObject);
            deleteTempFile(tempFile);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void deepParse(Element parent,JsonObject jsonObject) throws IOException {
        if(hasChildNodes(parent)) {
            JsonObject jsonObject1=new JsonObject();
            jsonObject.add(jsonObject1,parent.getTagName());
            NodeList childNodes = parent.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Element child=(Element)childNodes.item(i);
                deepParse(child,jsonObject1);
            }
        }
        else{
            jsonObject.add(parent.getTextContent(),parent.getTagName());
        }
        //TODO make attribute parsing
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
