package com.kaniademianchuk.ui;

import java.util.Scanner;

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

    interface Command {
        void run(String command);
    }
}
