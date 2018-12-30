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
				.findRegex("^.*ardmediathek.de/.*(?:\\?|&)documentId=(\\d+).*$");

		if (flow.toString() == null)
			return null;

		String streamJson = flow
				.formatContent("http://www.ardmediathek.de/play/media/%s?devicetype=pc")
				.readUrl()
				.resolveXPathInJson("//_mediaStreamArray[last()]/_stream")
				.toString();

		return streamJson;
	}

}
