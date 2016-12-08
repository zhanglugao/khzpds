package com.khzpds.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBElement;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Text;

public class Docx4jUtil {

	public static WordprocessingMLPackage getTemplate(String name) throws Docx4JException, FileNotFoundException {  
	    WordprocessingMLPackage template = WordprocessingMLPackage.load(new FileInputStream(new File(name)));  
	    return template;  
	}
	
	private static List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {  
	    List<Object> result = new ArrayList<Object>();  
	    if (obj instanceof JAXBElement) obj = ((JAXBElement<?>) obj).getValue();  
	  
	    if (obj.getClass().equals(toSearch))  
	        result.add(obj);  
	    else if (obj instanceof ContentAccessor) {  
	        List<?> children = ((ContentAccessor) obj).getContent();  
	        for (Object child : children) {  
	            result.addAll(getAllElementFromObject(child, toSearch));  
	        }  
	  
	    }  
	    return result;  
	}  
	
	public static void replacePlaceholder(WordprocessingMLPackage template, Map<String,String> replaceMap) {  
	    List<Object> texts = getAllElementFromObject(template.getMainDocumentPart(), Text.class);  
	  
	    for (Object text : texts) {  
	        Text textElement = (Text) text;  
	        System.out.println(textElement.getValue());
	        for(Entry<String, String> entry:replaceMap.entrySet()){
	        	if (textElement.getValue().equals(entry.getKey())) {  
		            textElement.setValue(entry.getValue());  
		            break;
		        }  
	        }
	    }  
	}  
	
	public static void writeDocxToStream(WordprocessingMLPackage template, String target) throws IOException, Docx4JException {  
	    File f = new File(target);  
	    template.save(f);  
	}  
	
	public static void main(String[] args) throws Docx4JException, IOException {
		WordprocessingMLPackage word=Docx4jUtil.getTemplate("D:\\video-template.docx");
		Docx4jUtil.replacePlaceholder(word, new HashMap<String, String>());
		Docx4jUtil.writeDocxToStream(word, "D:\\2.docx");
	}
}
