package com.kaniademianchuk.ui;

import com.kaniademianchuk.util.MockUtil;
import com.kaniademianchuk.util.PatternUtil;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class AbstractManipulatorTest {

    private final static String PREFIX = "[AbstractManipulator]";
    private AbstractManipulator manipulator;

    @Test
    void promptStringTest() {
        String prompt = "abstract prompt: ";
        String response = "response";

        MockUtil.mockCommands(response);
        this.initializeManipulator();
        String expectedOutput = manipulator.promptString(prompt);

        assertEquals(expectedOutput, response);
    }

    @Test
    void promptOneFromManyTest() {
        String prompt = "abstract prompt (one|two):";
        String choice1 = "one";
        String choice2 = "two";
        String preferredChoice = choice1;

        MockUtil.mockCommands(preferredChoice);
        this.initializeManipulator();
        String expectedOutput = manipulator.promptOneFromMany(prompt, choice1, choice2);

        assertEquals(expectedOutput, preferredChoice);
    }

    @Test
    void matchFirstIntegerTest() {
        Optional<Integer> id = Optional.of(new Integer(1));
        String input = "device " + id.get();
        Pattern pattern = PatternUtil.DEVICE_PATTERN;
        this.initializeManipulator();

        Optional<Integer> response = manipulator.matchFirstInteger(pattern, input);

        assertEquals(id, response);
    }

    @Test
    void isNotIntegerTest() {
        String input = "device one";
        Pattern pattern = PatternUtil.DEVICE_PATTERN;
        this.initializeManipulator();

        Optional<Integer> response = manipulator.matchFirstInteger(pattern, input);

        assertEquals(response, Optional.empty());
    }

    @Test
    void doesNotMatchPatternTest() {
        Optional<Integer> id = Optional.of(new Integer(1));
        String input = "group " + id.get();
        Pattern pattern = PatternUtil.DEVICE_PATTERN;
        this.initializeManipulator();

        Optional<Integer> response = manipulator.matchFirstInteger(pattern, input);

        assertNotEquals(id, response);
        assertEquals(response, Optional.empty());
    }

    @Test
    void getPrefixTest() {
        this.initializeManipulator();

        assertEquals(AbstractManipulatorTest.PREFIX, manipulator.getPrefix());
    }

    private void initializeManipulator() {
        manipulator = new AbstractManipulator(new Scanner(System.in)) {
            @Override
            String getPrefix() {
                return AbstractManipulatorTest.PREFIX;
            }
        };
    }
}