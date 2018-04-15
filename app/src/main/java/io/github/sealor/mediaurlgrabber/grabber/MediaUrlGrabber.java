package io.github.sealor.mediaurlgrabber.grabber;


public class MediaUrlGrabber extends AbstractGrabber {

	public MediaUrlGrabber() {
		super(
				new ArdMediathek(
						new DasErsteMediathek(null)
				));
	}

	@Override
	protected String grab(String url) {
		return null;
	}

}
