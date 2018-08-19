package io.github.sealor.mediaurlgrabber;

import org.junit.Test;

import io.github.sealor.mediaurlgrabber.grabber.Youtube;

import static io.github.sealor.mediaurlgrabber.helper.StringStartsWithRegex.startsWithRegex;
import static org.junit.Assert.assertThat;

public class YoutubeTest {

	public Youtube youtube = new Youtube(null);

	@Test
	public void testUrlGrab() {
		String videoUrl = "https://www.youtube.com/watch?v=2MpSnHv7UVk";

		String mp4Url = this.youtube.grab(videoUrl);

		assertThat(mp4Url, startsWithRegex("https://r2---sn-4g5e[0-9a-z]{4}[.]googlevideo[.]com/videoplayback[?]"));
	}

	@Test
	public void testAppUrlGrab() {
		String videoUrl = "https://youtu.be/2MpSnHv7UVk";

		String mp4Url = this.youtube.grab(videoUrl);

		assertThat(mp4Url, startsWithRegex("https://r2---sn-4g5e[0-9a-z]{4}[.]googlevideo[.]com/videoplayback[?]"));
	}

}
