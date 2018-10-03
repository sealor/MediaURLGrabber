package io.github.sealor.mediaurlgrabber.lib;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.github.sealor.mediaurlgrabber.lib.helper.StringStartsWithRegex.startsWithRegex;
import static org.junit.Assert.assertThat;

public class ArdMediathekTest {

	public ArdMediathek ardMediathek = new ArdMediathek(null);

	@Test
	public void testArdMediathekInAllerFreundschaft() throws IOException {
		String videoUrl = "https://www.ardmediathek.de";
		try (InputStream is = openUrl("https://www.ardmediathek.de/tv/In-aller-Freundschaft/Sendung?documentId=13258516")) {
			String inAllerFreundschaftMainPage = convertStreamToString(is);
			videoUrl += resolveMatch(inAllerFreundschaftMainPage, "<a href=\"(/tv/In-aller-Freundschaft/[^\"]+)\" class=\"mediaLink\">");
			videoUrl = decodeHtml(videoUrl);
		}

		String mp4Url = this.ardMediathek.grab(videoUrl);

		assertThat(mp4Url, startsWithRegex("https://.*\\.akamaihd\\.net/.*\\.mp4"));
	}

	@Test
	public void testDasErsteMediathekInAllerFreundschaft() throws IOException {
		String videoUrl = "http://mediathek.daserste.de";
		try (InputStream is = openUrl("http://mediathek.daserste.de/In-aller-Freundschaft/Sendung?documentId=6032920&topRessort&bcastId=6032920")) {
			String inAllerFreundschaftMainPage = convertStreamToString(is);
			videoUrl += resolveMatch(inAllerFreundschaftMainPage, "<a href=\"(/In-aller-Freundschaft/Folge-[^\"]+)\" class=\"mediaLink\">");
			videoUrl = decodeHtml(videoUrl);
		}

		String mp4Url = this.ardMediathek.grab(videoUrl);

		assertThat(mp4Url, startsWithRegex("https://.*\\.akamaihd\\.net/.*\\.mp4"));
	}

	@Test
	public void testArdMediathekInasNacht() throws IOException {
		String videoUrl = "https://www.ardmediathek.de";
		try (InputStream is = openUrl("https://www.ardmediathek.de/tv/Inas-Nacht/Sendung?documentId=52544614&bcastId=52544614")) {
			String inAllerFreundschaftMainPage = convertStreamToString(is);
			videoUrl += resolveMatch(inAllerFreundschaftMainPage, "<a href=\"(/tv/Inas-Nacht/.*/NDR-Fernsehen/[^\"]+)\" class=\"mediaLink\">");
			videoUrl = decodeHtml(videoUrl);
		}

		String mp4Url = this.ardMediathek.grab(videoUrl);

		assertThat(mp4Url, startsWithRegex("https://.*\\.akamaihd\\.net/.*\\.mp4"));
	}

	private InputStream openUrl(String urlString) {
		try {
			URL url = new URL(urlString);
			return url.openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String convertStreamToString(InputStream is) {
		Scanner s = new Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	private String resolveMatch(String string, String pattern) {
		Matcher m = Pattern.compile(pattern).matcher(string);
		return m.find() ? m.group(1) : "";
	}

	private String decodeHtml(String html) {
		return html.replaceAll("&amp;", "&");
	}

}
