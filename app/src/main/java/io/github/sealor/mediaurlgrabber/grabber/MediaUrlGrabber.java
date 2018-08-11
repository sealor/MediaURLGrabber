package io.github.sealor.mediaurlgrabber.grabber;


public class MediaUrlGrabber extends AbstractGrabber {

	public MediaUrlGrabber() {
		super(new ArdMediathek(new Youtube(null)));
	}

	@Override
	protected String grab(String url) {
		return null;
	}

}
