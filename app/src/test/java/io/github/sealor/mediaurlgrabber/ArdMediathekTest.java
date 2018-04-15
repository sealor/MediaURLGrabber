package io.github.sealor.mediaurlgrabber;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;

import io.github.sealor.mediaurlgrabber.grabber.ArdMediathek;

import static org.junit.Assert.assertEquals;

public class ArdMediathekTest {

	public ArdMediathek ardMediathek = new ArdMediathek();

	@Test
	public void test() throws IOException, ParseException {
		String videoUrl = "http://www.ardmediathek.de/tv/In-aller-Freundschaft-Die-jungen-%C3%84rzte/Folge-134-Unentbehrlich/Das-Erste/Video?bcastId=24966374&documentId=51049740";

		String mp4Url = this.ardMediathek.resolveMp4Url(videoUrl);

		String expectedMp4Url = "https://pdvideosdaserste-a.akamaihd.net/int/2018/02/23/064efa44-438c-4b12-ab66-93cef65d2e0a/960-1.mp4";
		assertEquals(expectedMp4Url, mp4Url);
	}

}
