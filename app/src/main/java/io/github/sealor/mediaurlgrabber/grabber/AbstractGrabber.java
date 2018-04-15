package io.github.sealor.mediaurlgrabber.grabber;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import io.github.sealor.mediaurlgrabber.JsonParser;

public class AbstractGrabber {

	protected JsonParser parser = new JsonParser();

	protected InputStream openUrl(String urlString) {
		try {
			URL url = new URL(urlString);
			return url.openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
