package com.github.jbequinn.poc.piglatin;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConversionVisitorTest {
	@Test
	void verifyConstant() {
		String[] convertedWords = new ConvertService().convert("Hello");

		Assertions.assertThat(String.join(" ", convertedWords))
				.isEqualTo("Ellohay");
	}

	@Test
	void verifyVowel() {
		String[] convertedWords = new ConvertService().convert("apple");

		Assertions.assertThat(String.join(" ", convertedWords))
				.isEqualTo("appleway");
	}

	@Test
	void verifyNoChanges() {
		String[] convertedWords = new ConvertService().convert("stairway");

		Assertions.assertThat(String.join(" ", convertedWords))
				.isEqualTo("stairway");
	}

	@Test
	void verifyWayWithPunctuation() {
		String[] convertedWords = new ConvertService().convert("stairway'");

		Assertions.assertThat(String.join(" ", convertedWords))
				.isEqualTo("tairwaysay'");
	}

	@Test
	void verifyOneCapitalization() {
		String[] convertedWords = new ConvertService().convert("Beach");

		Assertions.assertThat(String.join(" ", convertedWords))
				.isEqualTo("Eachbay");
	}

	@Test
	void verifyTwoCapitalization() {
		String[] convertedWords = new ConvertService().convert("McCloud");

		Assertions.assertThat(String.join(" ", convertedWords))
				.isEqualTo("CcLoudmay");
	}

	@Test
	void verifyPunctuationMiddle() {
		String[] convertedWords = new ConvertService().convert("can't");

		Assertions.assertThat(String.join(" ", convertedWords))
				.isEqualTo("antca'y");
	}

	@Test
	void verifyPunctuationEnd() {
		String[] convertedWords = new ConvertService().convert("end.");

		Assertions.assertThat(String.join(" ", convertedWords))
				.isEqualTo("endway.");
	}

	@Test
	void verifyHyphens() {
		String[] convertedWords = new ConvertService().convert("this-thing");

		Assertions.assertThat(String.join(" ", convertedWords))
				.isEqualTo("histay-hingtay");
	}

	@Test
	void verifyMultipleWordsSplit() {
		String[] convertedWords = new ConvertService()
				.convert("straw", "aw", "Qo'noS", "Goodbye", "good-ol'");

		Assertions.assertThat(String.join(" ", convertedWords))
				.isEqualTo("trawsay awway Onos'Qay Oodbyegay oodgay-olway'");
	}

	@Test
	void verifyMultipleWordsSingleLine() {
		String[] convertedWords = new ConvertService()
				.convert("straw aw Qo'noS Goodbye good-ol'");

		Assertions.assertThat(String.join(" ", convertedWords))
				.isEqualTo("trawsay awway Onos'Qay Oodbyegay oodgay-olway'");
	}
}
