package com.trelloiii;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.IllegalFormatException;

public abstract class AbstractParser implements Parser {
    protected String filePath;
    protected File fileToParse;
    public abstract void parse();
    public AbstractParser(String filePath) {
        this.filePath = filePath;
        fileToParse=new File(filePath);
    }
    protected void generateOutputFile(Object writable) throws IOException,IllegalArgumentException{
        String fileName=fileToParse.getName();
        File out;
        if(fileName.endsWith(".xml"))
            out=new File(fileToParse.getParentFile()+"/"+"generated_json.json");
        else if(fileName.endsWith(".json"))
            out=new File(fileToParse.getParentFile()+"/"+"generated_xml.xml");
        else
            throw new IllegalArgumentException("supports only json and xml");

        FileWriter writer=new FileWriter(out);
        BufferedWriter bufferedWriter=new BufferedWriter(writer);
        bufferedWriter.write(writable.toString());
        bufferedWriter.close();
        writer.close();
    }
    protected void deleteTempFile(File temp) throws IOException {
        Files.deleteIfExists(temp.toPath());
    }
}
