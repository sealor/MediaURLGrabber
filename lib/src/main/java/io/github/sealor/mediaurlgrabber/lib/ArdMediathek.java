package io.github.sealor.mediaurlgrabber.lib;

import io.github.sealor.mediaurlgrabber.lib.flow.Flow;

public class ArdMediathek extends AbstractGrabber {

	public ArdMediathek(AbstractGrabber grabber) {
		super(grabber);
	}

	public String grab(String videoPageUrl) {
		if (videoPageUrl == null)
			return null;

		Flow flow = new Flow(videoPageUrl)
				.tryToFindRegex("^.*ardmediathek.de/.*/player/(.*?)/?$");

		if (flow.toString() == null)
			return null;

		String streamJson = flow
				.formatContent("https://www.ardmediathek.de/ard/player/%s")
				.readUrl()
				.findRegex("window\\.__APOLLO_STATE__\\s*=\\s*(\\{.*?\\});")
				.resolveXPathInJson("//json[../../__typename/text() = 'MediaStreamArray'][../../_quality = string(math:max(//_quality[. != 'auto']/text()))][last()]/text()")
				.toString();

		return streamJson;
	}

}
