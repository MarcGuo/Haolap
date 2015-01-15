package com.haolap.message.xml.impl;

import java.util.List;
import java.util.Vector;

import com.haolap.message.xml.Document;
import com.haolap.message.xml.Element;

class Dom4JElement implements Element {
	private org.dom4j.Element impl;

	protected org.dom4j.Element getImpl() {
		return impl;
	}

	protected void setImpl(org.dom4j.Element impl) {
		this.impl = impl;
	}

	public Dom4JElement(org.dom4j.Element impl) {
		this.impl = impl;
	}

	@Override
	public Element addElement(String name) {
		return new Dom4JElement(impl.addElement(name));
	}

	@Override
	public void setText(String text) {
		impl.setText(text);
	}

	@Override
	public String getText() {
		return impl.getText();
	}

	@Override
	public void setName(String name) {
		impl.setName(name);
	}

	@Override
	public String getName() {
		return impl.getName();
	}

	@Override
	public Element getParent() {
		return new Dom4JElement(this.impl.getParent());
	}

	@Override
	public List<Element> selectElements(String xpathExpression) {
		List<? extends org.dom4j.Node> result = this.impl
				.selectNodes(xpathExpression);
		return castResult(result);
	}

	@Override
	public List<Element> selectElements(String xpathExpression,
			String comparisonXPathExpression) {
		List<? extends org.dom4j.Node> result = this.impl.selectNodes(
				xpathExpression, comparisonXPathExpression);
		return castResult(result);
	}

	@Override
	public List<Element> selectElements(String xpathExpression,
			String comparisonXPathExpression, boolean removeDuplicates) {
		List<? extends org.dom4j.Node> result = this.impl.selectNodes(
				xpathExpression, comparisonXPathExpression, removeDuplicates);
		return castResult(result);
	}

	private List<Element> castResult(List<? extends org.dom4j.Node> result) {
		List<Element> newReuslt = new Vector<Element>();
		for (org.dom4j.Node node : result) {
			newReuslt.add(new Dom4JElement((org.dom4j.Element) node));
		}
		return newReuslt;
	}

	@Override
	public List<Element> elements(String name) {
		List<org.dom4j.Element> result = this.impl.elements(name);
		return castResult(result);
	}

	@Override
	public List<Element> elements() {
		return castResult(this.impl.elements());
	}

	@Override
	public Document getDocument() {
		return new Dom4JDocument(this.impl.getDocument());
	}

	@Override
	public Element selectSingleElement(String xpathExpression) {
		org.dom4j.Element el = (org.dom4j.Element) this.impl
				.selectSingleNode(xpathExpression);
		return el == null ? null : new Dom4JElement(el);
	}

}
