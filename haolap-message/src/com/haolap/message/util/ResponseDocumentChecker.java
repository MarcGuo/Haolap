package com.haolap.message.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.haolap.message.xml.Document;
import com.haolap.message.xml.DocumentFactory;
import com.haolap.message.xml.Element;

public class ResponseDocumentChecker {
	private Document document;	
	private static final String TRUE = "true";

	public ResponseDocumentChecker(String xmlStr) {
		this.document = DocumentFactory.createDocumentFromStrStealthily(xmlStr);
	}

	public ResponseDocumentChecker(Document document) {
		this.document = document;
	}

	public boolean isSuccessful() {

		Element root = document.getRootElement();
		if (root != null) {
			Element success = root.selectSingleElement("success");
			if (success != null && TRUE.equals(success.getText())) {
				return true;
			}
		}
		return false;
	}

	public void exportError(PrintStream out) {
		out.println(getErrorMsg());
	}

	public List<String> getErrorMsgs() {
		List<String> errorMsgs = new ArrayList<String>();
		Element root = document.getRootElement();
		if (root != null) {
			List<Element> errors = root.elements("errors");
			if (errors.size() == 1) {
				List<Element> errorMsg = errors.get(0).elements();
				for (Element element : errorMsg) {
					errorMsgs.add(element.getText());
				}
			}

		}
		return errorMsgs;
	}

	public String getErrorMsg() {
		List<String> errors = this.getErrorMsgs();
		StringBuilder stringBuilder = new StringBuilder();
		for (String error : errors) {
			stringBuilder.append(error).append('\n');
		}
		if (stringBuilder.length() > 0) {
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		}
		return stringBuilder.toString();
	}

	public boolean tryingToExportError(PrintStream out) {
		if (this.isSuccessful())
			return false;
		else {
			this.exportError(out);
			return true;
		}
	}

	public Document getDocument() {
		return this.document;
	}

	public Element getRootElement() {
		return document.getRootElement();
	}
}
