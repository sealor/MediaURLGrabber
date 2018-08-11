package io.github.sealor.mediaurlgrabber;

import org.junit.Test;

import io.github.sealor.mediaurlgrabber.grabber.Youtube;

import static org.junit.Assert.assertTrue;

public class YoutubeTest {

	public Youtube youtube = new Youtube(null);

	@Test
	public void testUrlGrab() {
		String videoUrl = "https://www.youtube.com/watch?v=2MpSnHv7UVk";

		String mp4Url = this.youtube.grab(videoUrl);

		String expectedMp4UrlBegin = "https://r2---sn-4g5edns6.googlevideo.com/videoplayback?";
		assertTrue(mp4Url.startsWith(expectedMp4UrlBegin));
	}

	@Test
	public void testAppUrlGrab() {
		String videoUrl = "https://youtu.be/2MpSnHv7UVk";

		String mp4Url = this.youtube.grab(videoUrl);

		String expectedMp4UrlBegin = "https://r2---sn-4g5e6nze.googlevideo.com/videoplayback?";
		assertTrue(mp4Url.startsWith(expectedMp4UrlBegin));
	}

}
