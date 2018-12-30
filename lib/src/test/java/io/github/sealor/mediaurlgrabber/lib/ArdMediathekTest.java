package io.github.sealor.mediaurlgrabber.lib;

import org.junit.Test;

import java.io.IOException;

import io.github.sealor.mediaurlgrabber.lib.flow.Flow;

import static io.github.sealor.mediaurlgrabber.lib.helper.StringStartsWithRegex.startsWithRegex;
import static org.junit.Assert.assertThat;

public class ArdMediathekTest {

	public ArdMediathek ardMediathek = new ArdMediathek(null);

	@Test
	public void testArdMediathekInAllerFreundschaft() throws IOException {
		Flow flow = new Flow("https://www.ardmediathek.de/tv/In-aller-Freundschaft/Sendung?documentId=13258516")
				.readUrl()
				.findRegex("<a href=\"(/tv/In-aller-Freundschaft/[^\"]+)\" class=\"mediaLink\">")
				.decodeHtml();
		String videoUrl = "https://www.ardmediathek.de" + flow;

		String mp4Url = this.ardMediathek.grab(videoUrl);

		assertThat(mp4Url, startsWithRegex("https://.*\\.akamaihd\\.net/.*\\.mp4"));
	}

	@Test
	public void testArdMediathekInasNacht() throws IOException {
		Flow flow = new Flow("https://www.ardmediathek.de/tv/Inas-Nacht/Sendung?documentId=52544614&bcastId=52544614")
				.readUrl()
				.findRegex("<a href=\"(/tv/Inas-Nacht/.*/NDR-Fernsehen/[^\"]+)\" class=\"mediaLink\">")
				.decodeHtml();
		String videoUrl = "https://www.ardmediathek.de" + flow;

		String mp4Url = this.ardMediathek.grab(videoUrl);

		assertThat(mp4Url, startsWithRegex("https://.*\\.akamaihd\\.net/.*\\.mp4"));
	}

}
