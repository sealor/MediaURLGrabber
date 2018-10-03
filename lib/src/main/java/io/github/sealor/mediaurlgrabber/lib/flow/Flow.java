package io.github.sealor.mediaurlgrabber.lib.flow;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.sealor.mediaurlgrabber.lib.json.Json;
import io.github.sealor.mediaurlgrabber.lib.json.JsonParser;

import static java.lang.String.format;

public class Flow {

	private final String content;

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

	public Flow findRegex(String groupPattern) {
		Matcher m = Pattern.compile(groupPattern).matcher(this.content);
		return new Flow(m.find() ? m.group(1) : null);
	}

	public Flow formatContent(String format) {
		return new Flow(format(format, this.content));
	}

	public Flow resolveJson(String path) {
		Json json = new JsonParser().parse(this.content);
		return new Flow(json.getJson(path).toString());
	}

	public Flow resolveUrlValue(String parameterName) {
		String url = this.content.replaceFirst("^[^?]*\\?", "");

		for (String parameter : url.split("&"))
			if (parameter.startsWith(parameterName + "="))
				return new Flow(parameter.replaceFirst(".*=", ""));
		throw new FlowException(parameterName + " in URL " + url + " not found");
	}

	public String toString() {
		return this.content;
	}
}
