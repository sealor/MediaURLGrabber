package io.github.sealor.mediaurlgrabber.lib.xpath;

import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;

public class MathNamespaceContext implements NamespaceContext {

	public static final String NAMESPACE = "http://exslt.org/math";
	public static final String PREFIX = "math";

	@Override
	public String getNamespaceURI(String s) {
		return NAMESPACE;
	}

	@Override
	public String getPrefix(String s) {
		return PREFIX;
	}

	@Override
	public Iterator getPrefixes(String s) {
		return null;
	}
}
