package com.haolap.message.xml;

import java.util.List;

public interface Element {
	public Element addElement(String name);

	public void setText(String text);

	public String getText();

	public void setName(String name);

	public String getName();

	public Element getParent();

	public Document getDocument();

	public List<Element> selectElements(String xpathExpression);

	public List<Element> selectElements(String xpathExpression,
                                        String comparisonXPathExpression);

	public List<Element> selectElements(String xpathExpression,
                                        String comparisonXPathExpression, boolean removeDuplicates);

	public Element selectSingleElement(String xpathExpression);
	
	public List<Element> elements(String name);

	public List<Element> elements();

}
