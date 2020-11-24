package com.github.jbequinn.poc.piglatin;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConversionVisitorTest {
	@Test
	void verifyConstant() {
		String[] convertedWords = new String[1];
		new ConversionVisitor.ConversationVisitors().convert(0, new String[]{"Hello"}, convertedWords);

		Assertions.assertThat(String.join(" ", convertedWords))
				.isEqualTo("Ellohay");
	}

	@Test
	void verifyVowel() {
		String[] convertedWords = new String[1];
		new ConversionVisitor.ConversationVisitors().convert(0, new String[]{"apple"}, convertedWords);

		Assertions.assertThat(String.join(" ", convertedWords))
				.isEqualTo("appleway");
	}

	@Test
	void verifyWay() {
		String[] convertedWords = new String[1];
		new ConversionVisitor.ConversationVisitors().convert(0, new String[]{"stairway"}, convertedWords);

		Assertions.assertThat(String.join(" ", convertedWords))
				.isEqualTo("stairway");
	}

	@Test
	void verifyOneCapitalization() {
		String[] convertedWords = new String[1];
		new ConversionVisitor.ConversationVisitors().convert(0, new String[]{"Beach"}, convertedWords);

		Assertions.assertThat(String.join(" ", convertedWords))
				.isEqualTo("Eachbay");
	}

	@Test
	void verifyTwoCapitalization() {
		String[] convertedWords = new String[1];
		new ConversionVisitor.ConversationVisitors().convert(0, new String[]{"McCloud"}, convertedWords);

		Assertions.assertThat(String.join(" ", convertedWords))
				.isEqualTo("CcLoudmay");
	}

	@Test
	void verifyPunctuationMiddle() {
		String[] convertedWords = new String[1];
		new ConversionVisitor.ConversationVisitors().convert(0, new String[]{"can't"}, convertedWords);

		Assertions.assertThat(String.join(" ", convertedWords))
				.isEqualTo("antca'y");
	}

	@Test
	void verifyPunctuationEnd() {
		String[] convertedWords = new String[1];
		new ConversionVisitor.ConversationVisitors().convert(0, new String[]{"end."}, convertedWords);

		Assertions.assertThat(String.join(" ", convertedWords))
				.isEqualTo("endway.");
	}

	@Test
	void verifyHyphens() {
		String[] convertedWords = new String[1];
		new ConversionVisitor.ConversationVisitors().convert(0, new String[]{"this-thing"}, convertedWords);

		Assertions.assertThat(String.join(" ", convertedWords))
				.isEqualTo("histay-hingtay");
	}

	@Test
	void verifyMultipleWords() {
		String[] input = {"straw", "aw", "Qo'noS", "Goodbye"};
		String[] convertedWords = new String[4];
		ConversionVisitor.ConversationVisitors visitors = new ConversionVisitor.ConversationVisitors();
		for (int wordIndex = 0; wordIndex<input.length; wordIndex++) {
			visitors.convert(wordIndex, input, convertedWords);
		}

		Assertions.assertThat(String.join(" ", convertedWords))
				.isEqualTo("trawsay awway Onos'Qay Oodbyegay");
	}
}
