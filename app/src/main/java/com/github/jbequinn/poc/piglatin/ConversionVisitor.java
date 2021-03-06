package com.github.jbequinn.poc.piglatin;

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
					// use a stringbuilder here to avoid creating new strings all the time
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
			// there could be many changes, so avoid creating one new string every time
			StringBuilder builder = new StringBuilder();
			for (int charIndex = 0; charIndex < destination[index].length(); charIndex++) {
				if (charIndex < original[index].length() && Character.isUpperCase(original[index].charAt(charIndex))) {
					builder.append(Character.toUpperCase(destination[index].charAt(charIndex)));
				} else {
					builder.append(destination[index].charAt(charIndex));
				}
			}
			destination[index] = builder.toString();
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
					// or do some kind of characters stack to avoid creating many new strings
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

				// just do the same whole thing again - but this time there will be no hyphens
				String[] dehyphenedConvertedWords = new ConversionService().convert(dehyphenedWords);

				destination[index] = String.join("-", dehyphenedConvertedWords);
			}
		}
	}
}
