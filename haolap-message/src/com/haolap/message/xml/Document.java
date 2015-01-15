package com.haolap.message.xml;

import com.haolap.message.xml.exception.DocumentParserException;


public interface Document {
	public Element getRootElement();
	public void setRootElement(Element el);
	public String getXMLEncoding();
	public void setXMLEncoding(String encoding);
	public Element addElement(String elementName);
	public String getXMLString();
	public void setDocumentFromText(String text) throws DocumentParserException;
	public boolean setDocumentFromTextStealthily(String text);
}
