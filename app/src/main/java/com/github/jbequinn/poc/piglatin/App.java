/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.github.jbequinn.poc.piglatin;

import java.util.Scanner;

public class App {
	public static void main(String[] args) {
		System.out.print("Enter some text: ");
		String text = new Scanner(System.in).nextLine();

		// verify input, etc

		ConversionService service = new ConversionService();
		System.out.println("The output is: " + String.join(" ", service.convert(text)));
	}
}
