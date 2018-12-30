package io.github.sealor.mediaurlgrabber.lib.xpath;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NamespaceExtensionContext implements javax.xml.namespace.NamespaceContext {

	public static final String MATH_NAMESPACE = "http://exslt.org/math";
	public static final String MATH_PREFIX = "math";

	public static final String STR_NAMESPACE = "http://exslt.org/strings";
	public static final String STR_PREFIX = "str";

	private final Map<String, String> namespaceToPrefixMap = new HashMap<>();
	private final Map<String, String> prefixToNamespaceMap = new HashMap<>();

	public NamespaceExtensionContext() {
		this.namespaceToPrefixMap.put(MATH_NAMESPACE, MATH_PREFIX);
		this.namespaceToPrefixMap.put(STR_NAMESPACE, STR_PREFIX);

		for (Map.Entry<String, String> entry : this.namespaceToPrefixMap.entrySet())
			this.prefixToNamespaceMap.put(entry.getValue(), entry.getKey());
	}

	@Override
	public String getNamespaceURI(String prefix) {
		return this.prefixToNamespaceMap.get(prefix);
	}

	@Override
	public String getPrefix(String namespace) {
		return this.namespaceToPrefixMap.get(namespace);
	}

	@Override
	public Iterator getPrefixes(String s) {
		return null;
	}

}
