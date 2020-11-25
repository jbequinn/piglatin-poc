package com.github.jbequinn.poc.piglatin;

import java.util.List;

public interface ConversionVisitor {
	void applyChange(int index, String[] original, String[] destination);

	default boolean isVowel(char character) {
		return "aeiouAEIOU".indexOf(character) != -1;
	}

	default boolean isPunctuation(char character) {
		return "'\",.:".indexOf(character) != -1;
	}

	class CopyVisitor implements ConversionVisitor {
		@Override
		public void applyChange(int index, String[] original, String[] destination) {
			// copy as a first step, but also do some tweaks:
			// 1. remove all punctuation (it will be re-added later)
			// 2. make it lowercase (it will be re-capitalized later)
			destination[index] = "";
			for (int charIndex = 0; charIndex < original[index].length(); charIndex++) {
				if (!isPunctuation(original[index].charAt(charIndex))) {
					destination[index] += Character.toLowerCase(original[index].charAt(charIndex));
				}
			}
		}
	}

	class ConstantMoverVisitor implements ConversionVisitor {
		@Override
		public void applyChange(int index, String[] original, String[] destination) {
			// not very correct, but let's say it's a consonant if it's not a vowel
			if (!isVowel(original[index].charAt(0)) && !original[index].endsWith("way")) {
				destination[index] = destination[index].substring(1) + destination[index].charAt(0);
			}
		}
	}

	class WAppenderVisitor implements ConversionVisitor {
		@Override
		public void applyChange(int index, String[] original, String[] destination) {
			if (isVowel(original[index].charAt(0)) && !original[index].endsWith("way")) {
				destination[index] = destination[index] + "w";
			}
		}
	}

	class AyAppenderVisitor implements ConversionVisitor {
		@Override
		public void applyChange(int index, String[] original, String[] destination) {
			if (!original[index].endsWith("way")) {
				destination[index] = destination[index] + "ay";
			}
		}
	}

	class CapitalizationVisitor implements ConversionVisitor {
		@Override
		public void applyChange(int index, String[] original, String[] destination) {
			for (int charIndex = 0; charIndex < original[index].length(); charIndex++) {
				if (Character.isUpperCase(original[index].charAt(charIndex))) {
					// strings are immutable - create a new one
					destination[index] = destination[index].substring(0, charIndex) +
							Character.toUpperCase(destination[index].charAt(charIndex)) +
							destination[index].substring(charIndex + 1);
				}
			}
		}
	}

	class PunctuationVisitor implements ConversionVisitor {
		@Override
		public void applyChange(int index, String[] original, String[] destination) {
			// this one should keep the same relative place from the end
			for (int charIndex = original[index].length() - 1; charIndex > 0; charIndex--) {
				if (isPunctuation(original[index].charAt(charIndex))) {
					int position = destination[index].length() - 1 - (original[index].length() - 1 - charIndex);
					// strings are immutable - create a new one
					destination[index] = destination[index].substring(0, position + 1) +
							original[index].charAt(charIndex) +
							destination[index].substring(position + 1);
				}
			}
		}
	}

	class HyphensVisitor implements ConversionVisitor {
		@Override
		public void applyChange(int index, String[] original, String[] destination) {
			if (original[index].contains("-")) {
				String[] dehyphenedWords = original[index].split("-");
				String[] dehyphenedConvertedWords = new String[dehyphenedWords.length];

				// just do the same whole thing again - but this time there will be no hyphens
				ConversationVisitors visitors = new ConversationVisitors();
				for (int wordIndex = 0; wordIndex < dehyphenedWords.length; wordIndex++) {
					visitors.convert(wordIndex, dehyphenedWords, dehyphenedConvertedWords);
				}

				destination[index] = String.join("-", dehyphenedConvertedWords);
			}
		}
	}

	class ConversationVisitors {
		private final List<ConversionVisitor> visitors = List.of(
				// first step: copy the word without changes
				new CopyVisitor(),
				// move start consonant to the end
				new ConstantMoverVisitor(),
				// add 'w'
				new WAppenderVisitor(),
				// add 'ay'
				new AyAppenderVisitor(),
				// punctuation
				new PunctuationVisitor(),
				// capitalization
				new CapitalizationVisitor(),
				// hyphens
				new HyphensVisitor()
		);

		public void convert(int index, String[] original, String[] destination) {
			visitors.forEach(visitor -> visitor.applyChange(index, original, destination));
		}
	}
}
