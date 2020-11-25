package com.github.jbequinn.poc.piglatin;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConversionVisitorTest {
	@Test
	void verifyConstant() {
		String[] convertedWords = new ConvertService().convert("Hello");

		Assertions.assertThat(convertedWords)
				.containsOnly("Ellohay");
	}

	@Test
	void verifyVowel() {
		String[] convertedWords = new ConvertService().convert("apple");

		Assertions.assertThat(convertedWords)
				.containsOnly("appleway");
	}

	@Test
	void verifyNoChanges() {
		String[] convertedWords = new ConvertService().convert("stairway");

		Assertions.assertThat(convertedWords)
				.containsOnly("stairway");
	}

	@Test
	void verifyWayWithPunctuation() {
		String[] convertedWords = new ConvertService().convert("stairway'");

		Assertions.assertThat(convertedWords)
				.containsOnly("tairwaysay'");
	}

	@Test
	void verifyOneCapitalization() {
		String[] convertedWords = new ConvertService().convert("Beach");

		Assertions.assertThat(convertedWords)
				.containsOnly("Eachbay");
	}

	@Test
	void verifyTwoCapitalization() {
		String[] convertedWords = new ConvertService().convert("McCloud");

		Assertions.assertThat(convertedWords)
				.containsOnly("CcLoudmay");
	}

	@Test
	void verifyPunctuationMiddle() {
		String[] convertedWords = new ConvertService().convert("can't");

		Assertions.assertThat(convertedWords)
				.containsOnly("antca'y");
	}

	@Test
	void verifyPunctuationEnd() {
		String[] convertedWords = new ConvertService().convert("end.");

		Assertions.assertThat(convertedWords)
				.containsOnly("endway.");
	}

	@Test
	void verifyHyphens() {
		String[] convertedWords = new ConvertService().convert("this-thing");

		Assertions.assertThat(convertedWords)
				.containsOnly("histay-hingtay");
	}

	@Test
	void verifyMultipleWordsSplit() {
		String[] convertedWords = new ConvertService()
				.convert("straw", "aw", "Qo'noS", "Goodbye", "good-ol'");

		Assertions.assertThat(convertedWords)
				.containsExactly("trawsay", "awway", "Onos'Qay", "Oodbyegay", "oodgay-olway'");
	}

	@Test
	void verifyMultipleWordsSingleLine() {
		String[] convertedWords = new ConvertService()
				.convert("straw aw Qo'noS Goodbye good-ol'");

		Assertions.assertThat(convertedWords)
				.containsExactly("trawsay", "awway", "Onos'Qay", "Oodbyegay", "oodgay-olway'");
	}
}
