package com.haolap.message.xml.impl;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.haolap.message.xml.Document;
import com.haolap.message.xml.Element;
import com.haolap.message.xml.exception.DocumentParserException;

public class Dom4JDocument implements Document {
	private org.dom4j.Document impl;

	public Dom4JDocument() {
		this.impl = DocumentHelper.createDocument();
	}

	public Dom4JDocument(org.dom4j.Document impl) {
		this.impl = impl;
	}

	protected org.dom4j.Document getImpl() {
		return impl;
	}

	protected void setImpl(org.dom4j.Document impl) {
		this.impl = impl;
	}

	@Override
	public Element getRootElement() {
		org.dom4j.Element element = impl.getRootElement();
		return element == null ? null : new Dom4JElement(element);

	}

	@Override
	public void setRootElement(Element el) {
		impl.setRootElement(((Dom4JElement) el).getImpl());
	}

	@Override
	public String getXMLEncoding() {
		return impl.getXMLEncoding();
	}

	@Override
	public void setXMLEncoding(String encoding) {
		impl.setXMLEncoding(encoding);
	}

	@Override
	public Element addElement(String elementName) {
		org.dom4j.Element element = impl.addElement(elementName);
		return new Dom4JElement(element);
	}

	@Override
	public String getXMLString() {
		return impl.asXML();
	}

	@Override
	public void setDocumentFromText(String text) throws DocumentParserException {
		try {
			org.dom4j.Document document = DocumentHelper.parseText(text);
			this.setImpl(document);
		} catch (DocumentException e) {
			// e.printStackTrace();
			throw new DocumentParserException(e);
		}
	}

	@Override
	public boolean setDocumentFromTextStealthily(String text) {
		try {
			org.dom4j.Document document = DocumentHelper.parseText(text);
			this.setImpl(document);
			return true;
		} catch (DocumentException e) {
			// e.printStackTrace();
			this.setImpl(DocumentHelper.createDocument());
			return false;
		}
	}
}
