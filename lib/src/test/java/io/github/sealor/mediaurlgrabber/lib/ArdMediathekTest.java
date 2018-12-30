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
		String lastShowUrl = new Flow("https://www.ardmediathek.de/ard/shows/Y3JpZDovL2Rhc2Vyc3RlLmRlL2luIGFsbGVyIGZyZXVuZHNjaGFmdA/in-aller-freundschaft")
				.readUrl()
				.findRegex("window\\.__APOLLO_STATE__\\s*=\\s*(\\{.*?\\});")
				.resolveXPathInJson("//*[broadcastedOn][str:translate(./broadcastedOn/text(), '-T:Z', '') = math:max(str:translate(//broadcastedOn/text(), '-T:Z', ''))][last()]/id/text()")
				.formatContent("https://www.ardmediathek.de/ard/player/%s/")
				.toString();

		String mp4Url = this.ardMediathek.grab(lastShowUrl);

		assertThat(mp4Url, startsWithRegex("https://.*\\.akamaihd\\.net/.*\\.mp4"));
	}

	@Test
	public void testArdMediathekInasNacht() throws IOException {
		String lastShowUrl = new Flow("https://www.ardmediathek.de/ard/shows/Y3JpZDovL2Rhc2Vyc3RlLm5kci5kZS8xNDA5/inas-nacht")
				.readUrl()
				.findRegex("window\\.__APOLLO_STATE__\\s*=\\s*(\\{.*?\\});")
				.resolveXPathInJson("//*[broadcastedOn][str:translate(./broadcastedOn/text(), '-T:Z', '') = math:max(str:translate(//broadcastedOn/text(), '-T:Z', ''))][last()]/id/text()")
				.formatContent("https://www.ardmediathek.de/ard/player/%s/")
				.toString();

		String mp4Url = this.ardMediathek.grab(lastShowUrl);

		assertThat(mp4Url, startsWithRegex("https://.*\\.akamaihd\\.net/.*\\.mp4"));
	}

}
