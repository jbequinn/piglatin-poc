# PigLatin-PoC

This repository is a "homework assignment" for a Java [Pig Latin](https://en.wikipedia.org/wiki/Pig_Latin) translator application. The application reads some text from the console, and prints it back translated.

## How to build
```
./gradlew clean build
```

## How to run
```
./gradlew run --console=plain
```

## Technical insights

It is maybe a bit overcomplicated in what it does, but I personally like it because:
- Additional changes can be "plugged in" by providing additional visitors.
- [SRP](https://en.wikipedia.org/wiki/Single-responsibility_principle): every visitor deals with only one specific change to the string.
- It's subjectively easier to work with. For example, each visitor can be debugged individually.

## Future improvements
- Each visitor could implement some method to determine if it's applicable for the current word. Something like `boolean isApplicable(String word)`. This could be used to just filter out unapplicable visitors, and this would decouple a bit more the conversion logic from the "check" logic.
