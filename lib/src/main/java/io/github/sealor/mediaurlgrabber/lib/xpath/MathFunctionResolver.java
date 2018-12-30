package io.github.sealor.mediaurlgrabber.lib.xpath;

import org.w3c.dom.NodeList;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunction;
import javax.xml.xpath.XPathFunctionException;
import javax.xml.xpath.XPathFunctionResolver;

import static io.github.sealor.mediaurlgrabber.lib.xpath.MathNamespaceContext.NAMESPACE;
import static io.github.sealor.mediaurlgrabber.lib.xpath.MathNamespaceContext.PREFIX;

public class MathFunctionResolver implements XPathFunctionResolver {

	@Override
	public XPathFunction resolveFunction(QName qName, int i) {
		if (qName.equals(new QName(NAMESPACE, "max", PREFIX)))
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

		return null;
	}

}
