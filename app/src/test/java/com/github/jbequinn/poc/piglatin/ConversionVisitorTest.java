package com.github.jbequinn.poc.piglatin;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConversionVisitorTest {
	// possible improvement: as these tests are almost the same, use
	// JUnit @ParameterizedTest to provide the input and the expected output

	@Test
	void verifyConstant() {
		String[] convertedWords = new ConversionService().convert("Hello");

		Assertions.assertThat(convertedWords)
				.containsOnly("Ellohay");
	}

	@Test
	void verifyVowel() {
		String[] convertedWords = new ConversionService().convert("apple");

		Assertions.assertThat(convertedWords)
				.containsOnly("appleway");
	}

	@Test
	void verifyNoChanges() {
		String[] convertedWords = new ConversionService().convert("stairway");

		Assertions.assertThat(convertedWords)
				.containsOnly("stairway");
	}

	@Test
	void verifyWayWithPunctuation() {
		String[] convertedWords = new ConversionService().convert("stairway'");

		Assertions.assertThat(convertedWords)
				.containsOnly("tairwaysay'");
	}

	@Test
	void verifyOneCapitalization() {
		String[] convertedWords = new ConversionService().convert("Beach");

		Assertions.assertThat(convertedWords)
				.containsOnly("Eachbay");
	}

	@Test
	void verifyTwoCapitalization() {
		String[] convertedWords = new ConversionService().convert("McCloud");

		Assertions.assertThat(convertedWords)
				.containsOnly("CcLoudmay");
	}

	@Test
	void verifyPunctuationMiddle() {
		String[] convertedWords = new ConversionService().convert("can't");

		Assertions.assertThat(convertedWords)
				.containsOnly("antca'y");
	}

	@Test
	void verifyPunctuationEnd() {
		String[] convertedWords = new ConversionService().convert("end.");

		Assertions.assertThat(convertedWords)
				.containsOnly("endway.");
	}

	@Test
	void verifyHyphens() {
		String[] convertedWords = new ConversionService().convert("this-thing");

		Assertions.assertThat(convertedWords)
				.containsOnly("histay-hingtay");
	}

	@Test
	void verifyMultipleWordsSplit() {
		String[] convertedWords = new ConversionService()
				.convert("straw", "aw", "Qo'noS", "Goodbye", "good-ol'");

		Assertions.assertThat(convertedWords)
				.containsExactly("trawsay", "awway", "Onos'Qay", "Oodbyegay", "oodgay-olway'");
	}

	@Test
	void verifyMultipleWordsSingleLine() {
		String[] convertedWords = new ConversionService()
				.convert("straw aw Qo'noS Goodbye good-ol'");

		Assertions.assertThat(convertedWords)
				.containsExactly("trawsay", "awway", "Onos'Qay", "Oodbyegay", "oodgay-olway'");
	}
}
