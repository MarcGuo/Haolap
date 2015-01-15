package com.haolap.message.xml;

import com.haolap.message.xml.exception.DocumentParserException;
import com.haolap.message.xml.impl.Dom4JDocument;

public class DocumentFactory {
	public static Document createDocument(){
		return new Dom4JDocument();
	}
	public static Document createDocumentFromStrStealthily(String str){
		Document document = new Dom4JDocument();
		document.setDocumentFromTextStealthily(str);
		return document;
	}
	public static Document createDocumentFromStr(String str) throws DocumentParserException{
		Document document = new Dom4JDocument();
		document.setDocumentFromText(str);
		return document;
	}
}
