package io.github.sealor.mediaurlgrabber.lib.flow;

import org.json.JSONObject;
import org.json.XML;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import io.github.sealor.mediaurlgrabber.lib.normalizer.KeyNormalizer;
import io.github.sealor.mediaurlgrabber.lib.xpath.MathFunctionResolver;
import io.github.sealor.mediaurlgrabber.lib.xpath.MathNamespaceContext;

import static java.lang.String.format;
import static java.net.URLDecoder.decode;

public class Flow {

	private final String content;

	private final XPathFactory xPathFactory = XPathFactory.newInstance();
	private final KeyNormalizer keyNormalizer = new KeyNormalizer();

	public Flow(String content) {
		this.content = content;
	}

	public Flow readUrl() {
		try {
			URL url = new URL(this.content);
			String urlContent = IOUtils.toString(url, "UTF-8");
			return new Flow(urlContent);
		} catch (Exception e) {
			throw new FlowException(this.content, e);
		}
	}

	public Flow decodeHtml() {
		return new Flow(this.content.replaceAll("&amp;", "&"));
	}

	public Flow decodeUrl() {
		try {
			return new Flow(decode(this.content, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public Flow findRegex(String groupPattern) {
		Matcher m = Pattern.compile(groupPattern).matcher(this.content);
		if (m.find())
			return new Flow(m.group(1));

		throw new FlowException(format("Regex '%s' not found in document:%n%s", groupPattern, this.content));
	}

	public Flow formatContent(String format) {
		return new Flow(format(format, this.content));
	}

	public Flow resolveUrlValue(String parameterName) {
		String url = this.content.replaceFirst("^[^?]*\\?", "");

		for (String parameter : url.split("&"))
			if (parameter.startsWith(parameterName + "="))
				return new Flow(parameter.replaceFirst(".*=", ""));
		throw new FlowException(parameterName + " in URL " + url + " not found");
	}

	public Flow resolveXPathInJson(String xpathString) {
		JSONObject jsonObject = new JSONObject(this.content);
		Map<String, Object> jsonMap = jsonObject.toMap();
		jsonMap = this.keyNormalizer.normalize(jsonMap);
		jsonObject = new JSONObject(jsonMap);

		String xml = XML.toString(jsonObject, "root");

		String result;
		try {
			XPath xPath = this.xPathFactory.newXPath();
			xPath.setNamespaceContext(new MathNamespaceContext());
			xPath.setXPathFunctionResolver(new MathFunctionResolver());

			result = xPath.evaluate(xpathString, new InputSource(new StringReader(xml)));
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}

		if (result.isEmpty())
			throw new FlowException(format("XPath '%s' not found in document:%n%s", xpathString, xml));

		return new Flow(result);
	}

	public String toString() {
		return this.content;
	}
}
