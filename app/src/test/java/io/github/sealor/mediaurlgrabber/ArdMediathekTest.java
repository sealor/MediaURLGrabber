package io.github.sealor.mediaurlgrabber;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;

import io.github.sealor.mediaurlgrabber.grabber.ArdMediathek;

import static org.junit.Assert.assertEquals;

public class ArdMediathekTest {

	public ArdMediathek ardMediathek = new ArdMediathek(null);

	@Test
	public void test1() throws IOException, ParseException {
		String videoUrl = "https://www.ardmediathek.de/tv/In-aller-Freundschaft-Die-jungen-%C3%84rzte/Folge-148-Kontrollverlust/Das-Erste/Video?bcastId=24966374&documentId=54549288";

		String mp4Url = this.ardMediathek.grab(videoUrl);

		String expectedMp4Url = "https://pdvideosdaserste-a.akamaihd.net/int/2018/06/25/1a6c11c3-a601-41f5-9041-eda48169a487/960-1.mp4";
		assertEquals(expectedMp4Url, mp4Url);
	}

}
