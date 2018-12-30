package io.github.sealor.mediaurlgrabber.lib.json;

public class JsonException extends RuntimeException {

	public JsonException(String message) {
		super(message);
	}

	public JsonException(String message, Throwable cause) {
		super(message, cause);
	}
}
