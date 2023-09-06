package com.automation.core.utilities.string;

import com.automation.core.utilities.number.IntUtils;
import com.automation.core.utilities.grid.PropUtils;

import java.util.List;

public class StrUtils {

    public static String randomWord(final int minCharacters, final int maxCharacters){
        final int wordLength = IntUtils.random(minCharacters, maxCharacters);

        return randomWord(wordLength);
    }

    public static String randomWord(final int numberOfCharacters){
        final StringBuilder word = new StringBuilder(numberOfCharacters);

        for(int i = 0; i < numberOfCharacters; i++){
            word.append(randomLetter());
        }

        return word.toString();
    }

    public static char randomLetter(){
        final int lowercaseASCIICode = IntUtils.random(97, 122);
        final int uppercaseASCIICode = IntUtils.random(65, 90);

        return IntUtils.random(0, 1) == 1 ? (char) uppercaseASCIICode : (char) lowercaseASCIICode;
    }


    public static String email() {
        return String.format(
                "automated.test.%s.%s.%s@mailinator.com",
                PropUtils.get("env"),
                PropUtils.get("user.name").orElseGet(() -> "someone").toLowerCase(),
                StrUtils.randomWord(3, 10)
        );
    }

    public static String prettyList(final List list)
    {
        final StringBuilder pretty = new StringBuilder("[\n");
        list.forEach(element -> pretty.append(element).append("\n"));
        pretty.append("]");

        return pretty.toString();
    }
}
