package io.github.sealor.mediaurlgrabber.grabber;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.sealor.mediaurlgrabber.Json;

import static java.lang.String.format;

public class DasErsteMediathek extends AbstractGrabber {

	public static final String DAS_ERSTE_MEDIATHEK_URL = "http://mediathek.daserste.de/play/media/%s?devicetype=pc";

	public DasErsteMediathek(AbstractGrabber grabber) {
		super(grabber);
	}

	public String grab(String videoPageUrl) {
		if (videoPageUrl == null)
			return null;

		Pattern ardUrlPattern = Pattern.compile("^.*mediathek.daserste.de/.*(\\?|&)documentId=(\\d+).*$");
		Matcher matcher = ardUrlPattern.matcher(videoPageUrl);

		if (!matcher.find())
			return null;

		String documentIdString = matcher.group(2);
		String deviceInfoUrl = format(DAS_ERSTE_MEDIATHEK_URL, documentIdString);
		Json json = parser.parse(openUrl(deviceInfoUrl));
		return json.getJson("_mediaArray[1]._mediaStreamArray[4]").getString("_stream");
	}

}
