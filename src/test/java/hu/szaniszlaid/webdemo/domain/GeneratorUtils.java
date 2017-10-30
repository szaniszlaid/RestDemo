package hu.szaniszlaid.webdemo.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

class GeneratorUtils {

    private static final List<Character> availableCharacters = Arrays.asList('a', 'á', 'b', 'c', 'd', 'e', 'é', 'f', 'g', 'h', 'i', 'í', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'ú', 'ü', 'ű', 'x', 'y', 'z', '-', '_', '\'', '"', '\\', ',', '@', '.');

    private static final Random random = new Random();


    /**
     * Generates a new random String. The generated string length is between 1 and @param maxLength
     * The characters come from {@link GeneratorUtils#availableCharacters}
     *
     * @param maxLength  Generated String maximal length is going to be in [1, maxLength) interval
     */
    public static String generateRandomString(int maxLength) {
        //[1, maxLength)
        int randomLength = random.nextInt(maxLength - 1) + 1;
        StringBuilder sb = new StringBuilder(randomLength);
        for (int i = 0; i < randomLength; i++) {
            Character nextRandomCharacter = availableCharacters.get(random.nextInt(availableCharacters.size() - 1));
            sb.append(nextRandomCharacter);
        }

        return sb.toString();
    }

}
