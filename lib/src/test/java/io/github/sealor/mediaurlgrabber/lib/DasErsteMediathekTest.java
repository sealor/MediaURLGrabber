package io.github.sealor.mediaurlgrabber.lib;

import org.junit.Test;

import java.io.IOException;

import io.github.sealor.mediaurlgrabber.lib.flow.Flow;

import static io.github.sealor.mediaurlgrabber.lib.helper.StringStartsWithRegex.startsWithRegex;
import static org.junit.Assert.assertThat;

public class DasErsteMediathekTest {

	public DasErsteMediathek dasErsteMediathek = new DasErsteMediathek(null);

	@Test
	public void testInAllerFreundschaft() throws IOException {
		Flow flow = new Flow("http://mediathek.daserste.de/In-aller-Freundschaft/Sendung?documentId=6032920&topRessort&bcastId=6032920")
				.readUrl()
				.findRegex("<a href=\"(/In-aller-Freundschaft/Folge-[^\"]+)\" class=\"mediaLink\">")
				.decodeHtml();
		String videoUrl = "http://mediathek.daserste.de" + flow;

		String mp4Url = this.dasErsteMediathek.grab(videoUrl);

		assertThat(mp4Url, startsWithRegex("https://.*\\.akamaihd\\.net/.*\\.mp4"));
	}

}
