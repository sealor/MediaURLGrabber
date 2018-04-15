package io.github.sealor.mediaurlgrabber.grabber;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.sealor.mediaurlgrabber.Json;

import static java.lang.String.format;

public class ArdMediathek extends AbstractGrabber {

	public static final String ARD_MEDIATHEK_URL = "http://www.ardmediathek.de/play/media/%s?devicetype=pc";

	public String resolveMp4Url(String videoPageUrl) {
		if (videoPageUrl == null)
			return null;

		Pattern ardUrlPattern = Pattern.compile("^.*ardmediathek.de/.*(\\?|&)documentId=(\\d+).*$");
		Matcher matcher = ardUrlPattern.matcher(videoPageUrl);

		if (!matcher.find())
			return null;

		String documentIdString = matcher.group(2);
		String deviceInfoUrl = format(ARD_MEDIATHEK_URL, documentIdString);
		Json json = parser.parse(openUrl(deviceInfoUrl));
		return json.getJson("_mediaArray[1]._mediaStreamArray[4]").getString("_stream");
	}

}