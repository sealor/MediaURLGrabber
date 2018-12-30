package io.github.sealor.mediaurlgrabber.lib;

import io.github.sealor.mediaurlgrabber.lib.flow.Flow;

public class DasErsteMediathek extends AbstractGrabber {

	public DasErsteMediathek(AbstractGrabber grabber) {
		super(grabber);
	}

	public String grab(String videoPageUrl) {
		if (videoPageUrl == null)
			return null;

		Flow flow = new Flow(videoPageUrl)
				.findRegex("^.*mediathek.daserste.de/.*(?:\\?|&)documentId=(\\d+).*$");

		if (flow.toString() == null)
			return null;

		String streamJson = flow
				.formatContent("http://www.ardmediathek.de/play/media/%s?devicetype=pc")
				.readUrl()
				.resolveJson("_mediaArray[0]._mediaStreamArray[4]._stream")
				.toString();

		if (streamJson.startsWith("["))
			return new Flow(streamJson).resolveJson("[0]").toString();
		else
			return streamJson;
	}

}
