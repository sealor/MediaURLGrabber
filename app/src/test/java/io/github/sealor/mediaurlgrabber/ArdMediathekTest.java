package io.github.sealor.mediaurlgrabber;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;

import io.github.sealor.mediaurlgrabber.grabber.ArdMediathek;

import static org.junit.Assert.assertEquals;

public class ArdMediathekTest {

	public ArdMediathek ardMediathek = new ArdMediathek(null);

	@Test
	public void testArdMediathekInAllerFreundschaft() throws IOException, ParseException {
		String videoUrl = "https://www.ardmediathek.de/tv/In-aller-Freundschaft-Die-jungen-%C3%84rzte/Folge-148-Kontrollverlust/Das-Erste/Video?bcastId=24966374&documentId=54549288";

		String mp4Url = this.ardMediathek.grab(videoUrl);

		String expectedMp4Url = "https://pdvideosdaserste-a.akamaihd.net/int/2018/06/25/1a6c11c3-a601-41f5-9041-eda48169a487/960-1.mp4";
		assertEquals(expectedMp4Url, mp4Url);
	}

	@Test
	public void testDasErsteMediathekInAllerFreundschaft() throws IOException, ParseException {
		String videoUrl = "http://mediathek.daserste.de/In-aller-Freundschaft-Die-jungen-%C3%84rzte/Folge-148-Kontrollverlust/Video?bcastId=24966374&documentId=54549288";

		String mp4Url = this.ardMediathek.grab(videoUrl);

		String expectedMp4Url = "https://pdvideosdaserste-a.akamaihd.net/int/2018/06/25/1a6c11c3-a601-41f5-9041-eda48169a487/960-1.mp4";
		assertEquals(expectedMp4Url, mp4Url);
	}

	@Test
	public void testInasNacht() throws IOException, ParseException {
		String videoUrl = "https://www.ardmediathek.de/tv/Inas-Nacht/Inas-Nacht-mit-Bettina-Zimmermann-Doro-/Das-Erste/Video?bcastId=3411450&documentId=54609802";

		String mp4Url = this.ardMediathek.grab(videoUrl);

		String expectedMp4Url = "https://mediandr-a.akamaihd.net/progressive/2018/0727/TV-20180727-1056-0200.hd.mp4";
		assertEquals(expectedMp4Url, mp4Url);
	}

}
