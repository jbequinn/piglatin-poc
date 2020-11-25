package com.github.jbequinn.poc.piglatin;

import java.util.List;

public class ConvertService {
	private final List<ConversionVisitor> visitors = List.of(
			// first step: copy the word
			new ConversionVisitor.CopyVisitor(),
			// move start consonant to the end
			new ConversionVisitor.ConstantMoverVisitor(),
			// add 'w' - only if the word starts with a consonant
			new ConversionVisitor.WAppenderVisitor(),
			// add 'ay' - it seems it's added always (except in -way words)
			new ConversionVisitor.AyAppenderVisitor(),
			// put back punctuation
			new ConversionVisitor.PunctuationVisitor(),
			// put back capitalization
			new ConversionVisitor.CapitalizationVisitor(),
			// process words with hyphens
			new ConversionVisitor.HyphensVisitor()
	);

	public String[] convert(String... original) {
		String[] input;
		// if it's only one element let's assume that it can be a sentence, etc. let's try to split
		if (original.length == 1) {
			input = original[0].split(" ");
		} else {
			// if it was split already, let's use that
			input = original;
		}
		String[] output = new String[input.length];
		for (int wordIndex = 0; wordIndex < input.length; wordIndex++) {
			convert(wordIndex, input, output);
		}
		return output;
	}

	private void convert(int index, String[] original, String[] destination) {
		visitors.forEach(visitor -> visitor.applyChange(index, original, destination));
	}
}
