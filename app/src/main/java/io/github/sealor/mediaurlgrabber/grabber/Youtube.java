package io.github.sealor.mediaurlgrabber.grabber;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

public class Youtube extends AbstractGrabber {

	public static final String YOUTUBE_INFO_URL = "http://www.youtube.com/get_video_info?video_id=%s&el=embedded&ps=default&eurl=&gl=US&hl=en";

	public Youtube(AbstractGrabber grabber) {
		super(grabber);
	}

	@Override
	public String grab(String videoPageUrl) {
		if (videoPageUrl == null)
			return null;

		Pattern urlPattern = Pattern.compile("^.*\\.youtube.[a-z]{1,3}/.*[?&]v=(\\w+).*$");
		Matcher matcher = urlPattern.matcher(videoPageUrl);

		if (!matcher.find())
			return null;

		String videoHash = matcher.group(1);
		String infoUrl = format(YOUTUBE_INFO_URL, videoHash);

		try {
			return tryToGrabWithInfoUrl(infoUrl);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String tryToGrabWithInfoUrl(String infoUrl) throws IOException {
		InputStream is = openUrl(infoUrl);
		String infoUrlParameters = convertStreamToString(is);
		is.close();

		String streamMapUrlencoded = resolveUrlParameter(infoUrlParameters, "url_encoded_fmt_stream_map");
		String streamMap = URLDecoder.decode(streamMapUrlencoded, "utf-8");

		String firstStream = streamMap.replaceFirst(",.*$", "");
		String streamUrlencoded = resolveUrlParameter(firstStream, "url");
		String streamUrl = URLDecoder.decode(streamUrlencoded, "utf-8");

		return streamUrl;
	}

	public static String convertStreamToString(InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	public static String resolveUrlParameter(String url, String parameterName) {
		for (String parameter : url.split("&"))
			if (parameter.startsWith(parameterName + "="))
				return parameter.replaceFirst(".*=", "");
		throw new RuntimeException(parameterName + " in URL " + url + " not found");
	}

}
