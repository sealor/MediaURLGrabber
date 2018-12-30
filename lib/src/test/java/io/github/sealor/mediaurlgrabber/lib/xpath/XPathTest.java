package io.github.sealor.mediaurlgrabber.lib.xpath;

import org.junit.Test;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import static org.junit.Assert.assertEquals;

public class XPathTest {

	@Test
	public void test() throws XPathExpressionException {
		String xml = "<root><num>2</num><num>3</num><num>1</num></root>";

		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xPath = xPathFactory.newXPath();

		xPath.setNamespaceContext(new MathNamespaceContext());
		xPath.setXPathFunctionResolver(new MathFunctionResolver());

		String result = xPath.evaluate("math:max(//num)", new InputSource(new StringReader(xml)));

		assertEquals("3", result);
	}

}
