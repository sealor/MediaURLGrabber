package io.github.sealor.mediaurlgrabber.lib;

import io.github.sealor.mediaurlgrabber.lib.flow.Flow;

public class Youtube extends AbstractGrabber {

	public Youtube(AbstractGrabber grabber) {
		super(grabber);
	}

	@Override
	public String grab(String videoPageUrl) {
		if (videoPageUrl == null)
			return null;


		Flow flow = new Flow(videoPageUrl)
				.tryToFindRegex("^.*(?:\\.youtube.[a-z]{1,3}/.*[?&]v=|youtu\\.be/)([\\w_]+).*$");

		if (flow.toString() == null)
			return null;

		return flow.formatContent("http://www.youtube.com/get_video_info?video_id=%s&el=embedded&ps=default&eurl=&gl=US&hl=en")
				.readUrl()
				.resolveUrlValue("url_encoded_fmt_stream_map")
				.decodeUrl()
				.findRegex("^([^,]*)")
				.resolveUrlValue("url")
				.decodeUrl()
				.toString();
	}

}
