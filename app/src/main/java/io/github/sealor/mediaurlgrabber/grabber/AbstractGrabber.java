package io.github.sealor.mediaurlgrabber.grabber;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import io.github.sealor.mediaurlgrabber.JsonParser;

public abstract class AbstractGrabber {

	private AbstractGrabber grabber;

	protected JsonParser parser = new JsonParser();

	public AbstractGrabber(AbstractGrabber grabber) {
		this.grabber = grabber;
	}

	public String grabMediaUrl(String videoPageUrl) {
		String mediaUrl = this.grab(videoPageUrl);

		if (mediaUrl != null)
			return mediaUrl;

		if (this.grabber != null)
			return this.grabber.grabMediaUrl(videoPageUrl);

		return null;
	}

	abstract protected String grab(String url);

	protected InputStream openUrl(String urlString) {
		try {
			URL url = new URL(urlString);
			return url.openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
