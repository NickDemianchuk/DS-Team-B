package com.kaniademianchuk.ui;

import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractManipulator {

    protected final Scanner reader;

    AbstractManipulator(Scanner reader) {
        this.reader = reader;
    }

    protected String promptString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String toggleName = reader.nextLine();
            if (toggleName.length() > 0) {
                return toggleName;
            }
        }
    }

    protected String promptOptionalString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String toggleName = reader.nextLine();
        }
    }

    protected String promptOneFromMany(String prompt, String... choices) {
        while (true) {
            System.out.print(prompt);
            String input = this.reader.nextLine();
            for (String choice : choices) {
                if (choice.equals(input)) {
                    return input;
                }
            }
        }
    }

    protected Optional<Integer> matchFirstInteger(Pattern pattern, String input) {
        Matcher m = pattern.matcher(input);
        if (!m.find()) {
            return Optional.empty();
        }
        Integer id = Integer.parseInt(m.group(1));
        return Optional.of(id);
    }

    interface Command {
        void run(String command);
    }
}
