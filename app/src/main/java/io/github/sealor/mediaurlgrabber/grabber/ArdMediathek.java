package io.github.sealor.mediaurlgrabber.grabber;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.sealor.mediaurlgrabber.Json;

import static java.lang.String.format;

public class ArdMediathek extends AbstractGrabber {

	public static final String ARD_MEDIATHEK_URL = "http://www.ardmediathek.de/play/media/%s?devicetype=pc";

	public ArdMediathek(AbstractGrabber grabber) {
		super(grabber);
	}

	public String grab(String videoPageUrl) {
		if (videoPageUrl == null)
			return null;

		Pattern ardUrlPattern = Pattern.compile("^.*(ardmediathek.de|mediathek.daserste.de)/.*(\\?|&)documentId=(\\d+).*$");
		Matcher matcher = ardUrlPattern.matcher(videoPageUrl);

		if (!matcher.find())
			return null;

		String documentIdString = matcher.group(3);
		String deviceInfoUrl = format(ARD_MEDIATHEK_URL, documentIdString);
		Json json = parser.parse(openUrl(deviceInfoUrl));

		Json streamJson = json.getJson("_mediaArray[1]._mediaStreamArray[4]");

		if (streamJson.get("_stream") instanceof List)
			return streamJson.getJson("_stream").getString(0);

		if (streamJson.get("_stream") instanceof String)
			return streamJson.getString("_stream");

		throw new RuntimeException("Unknown type in '_stream': " + streamJson.toString());
	}

}
