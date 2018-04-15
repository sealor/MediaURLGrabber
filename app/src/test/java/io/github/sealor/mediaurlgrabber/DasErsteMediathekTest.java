package io.github.sealor.mediaurlgrabber;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;

import io.github.sealor.mediaurlgrabber.grabber.DasErsteMediathek;

import static org.junit.Assert.assertEquals;

public class DasErsteMediathekTest {

	public DasErsteMediathek dasErsteMediathek = new DasErsteMediathek(null);

	@Test
	public void test() throws IOException, ParseException {
		String videoUrl = "http://mediathek.daserste.de/In-aller-Freundschaft-Die-jungen-%C3%84rzte/Folge-137-Rettung/Video?bcastId=24966374&documentId=51588142";

		String mp4Url = this.dasErsteMediathek.grab(videoUrl);

		String expectedMp4Url = "https://pdvideosdaserste-a.akamaihd.net/int/2018/03/22/ae622732-4496-4d8b-93a4-19bede9404fe/960-1.mp4";
		assertEquals(expectedMp4Url, mp4Url);
	}

}
