package io.github.sealor.mediaurlgrabber.lib.flow;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * This class mimics apache commons IOUtils to reduce file size.
 */
public class IOUtils {

	public static String toString(URL url, String encoding) throws IOException {
		try (InputStream is = url.openStream()) {
			Scanner s = new Scanner(is, encoding).useDelimiter("\\A");
			return s.hasNext() ? s.next() : null;
		}
	}

}
