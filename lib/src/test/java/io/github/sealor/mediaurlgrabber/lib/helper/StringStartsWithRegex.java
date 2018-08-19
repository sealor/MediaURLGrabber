package io.github.sealor.mediaurlgrabber.lib.helper;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.SubstringMatcher;

public class StringStartsWithRegex extends SubstringMatcher {

	public StringStartsWithRegex(String substring) {
		super(substring);
	}

	@Override
	protected boolean evalSubstringOf(String s) {
		return s.matches("^" + substring + ".*$");
	}

	@Override
	protected String relationship() {
		return "starting with regex";
	}

	@Factory
	public static Matcher<String> startsWithRegex(String prefix) {
		return new StringStartsWithRegex(prefix);
	}

}