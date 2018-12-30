package io.github.sealor.mediaurlgrabber.lib.xpath;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunction;
import javax.xml.xpath.XPathFunctionException;
import javax.xml.xpath.XPathFunctionResolver;

import static io.github.sealor.mediaurlgrabber.lib.xpath.NamespaceExtensionContext.MATH_NAMESPACE;
import static io.github.sealor.mediaurlgrabber.lib.xpath.NamespaceExtensionContext.MATH_PREFIX;
import static io.github.sealor.mediaurlgrabber.lib.xpath.NamespaceExtensionContext.STR_NAMESPACE;
import static io.github.sealor.mediaurlgrabber.lib.xpath.NamespaceExtensionContext.STR_PREFIX;

public class FunctionExtensionResolver implements XPathFunctionResolver {

	@Override
	public XPathFunction resolveFunction(QName qName, int i) {
		if (qName.equals(new QName(MATH_NAMESPACE, "max", MATH_PREFIX)))
			return new XPathFunction() {

				@Override
				public Object evaluate(List list) throws XPathFunctionException {
					assert list.size() == 1;
					NodeList nodeList = (NodeList) list.get(0);

					assert nodeList.getLength() > 0;
					double max = Double.valueOf(nodeList.item(0).getTextContent());

					for (int i = 1; i < nodeList.getLength(); i++) {
						double nextValue = Double.valueOf(nodeList.item(i).getTextContent());
						if (nextValue > max)
							max = nextValue;
					}

					return max;
				}

			};

		if (qName.equals(new QName(STR_NAMESPACE, "translate", STR_PREFIX)))
			return new XPathFunction() {

				@Override
				public Object evaluate(List list) throws XPathFunctionException {
					assert list.size() == 3;
					final NodeList nodeList = (NodeList) list.get(0);
					final String searchStr = (String) list.get(1);
					final String replaceStr = (String) list.get(2);

					return new NodeList() {

						@Override
						public Node item(int num) {
							Node internalNode = nodeList.item(num);

							String str = internalNode.getTextContent();
							str = translate(str);

							Node node = internalNode.cloneNode(true);
							node.setTextContent(str);

							return node;
						}

						private String translate(String str) {
							for (int i = 0; i < searchStr.length(); i++) {
								String pattern = Pattern.quote(String.valueOf(searchStr.charAt(i)));
								String replacement = replaceStr.length() > i ? String.valueOf(replaceStr.charAt(i)) : "";
								str = str.replaceAll(pattern, replacement);
							}
							return str;
						}

						@Override
						public int getLength() {
							return nodeList.getLength();
						}

					};
				}

			};


		return null;
	}

}
