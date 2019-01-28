package com.kaniademianchuk.ui;

import com.kaniademianchuk.api.ITogglable;
import com.kaniademianchuk.model.*;
import com.kaniademianchuk.util.MockEventHandler;
import com.kaniademianchuk.util.MockUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class ScheduleManipulatorTest {

    public static final String PROMPT = "Choose a command: list, create, remove, exit: ";
    private ITogglable device;
    private ScheduledTask scheduledTask;
    private Manager<ITogglable> deviceManager;
    private Manager<ScheduledTask> taskManager;
    private ScheduleManipulator manipulator;
    private ScheduledTaskType taskType;

    @BeforeEach
    void setUp() {
        device = new DefaultTogglable("smart switch", true, new MockEventHandler());
        deviceManager = new Manager<>();
        deviceManager.addDevice(device);
        taskType = ScheduledTaskType.TOGGLE;
        scheduledTask = new ScheduledTask("toggle", deviceManager, device.getId(), taskType);
        taskManager = new Manager<>();
        taskManager.addDevice(scheduledTask);
    }

    @Test
    void promptString() {
        String response = "list";
        MockUtil.mockCommands(response);
        this.initializeManipulator();

        String expectedOutput = manipulator.promptString(ScheduleManipulatorTest.PROMPT);

        assertEquals(expectedOutput, response);
    }

    @Test
    void promptOneFromMany() {
        String choice1 = "list";
        String choice2 = "create";
        String choice3 = "remove";
        String choice4 = "exit";
        String preferredChoice = choice2;
        MockUtil.mockCommands(preferredChoice);
        this.initializeManipulator();

        String expectedOutput =
                manipulator.promptOneFromMany(ScheduleManipulatorTest.PROMPT, choice1, choice2, choice3, choice4);

        assertEquals(expectedOutput, preferredChoice);
    }

    @Test
    void promptUnexistingOneFromMany() {
        String choice1 = "list";
        String choice2 = "create";
        String choice3 = "remove";
        String choice4 = "exit";
        String preferredChoice = "do nothing";
        MockUtil.mockCommands(preferredChoice);
        this.initializeManipulator();
        String expectedOutput;

        try {
            expectedOutput =
                    manipulator.promptOneFromMany(ScheduleManipulatorTest.PROMPT, choice1, choice2, choice3, choice4);
            fail("exception is not thrown");
        } catch (NoSuchElementException e) {
            expectedOutput = e.getMessage();
        }

        assertNotEquals(preferredChoice, expectedOutput);
    }

    @Test
    void runToList() {
        String input1 = "list";
        String input2 = "exit";
        MockUtil.mockCommands(input1, input2);
        this.initializeManipulator();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        manipulator.run(new String());
        String output = baos.toString();

        assertTrue(output.contains(taskManager.getAllDevices().toString()));
    }

    @Test
    void runToCreate() {
        String input1 = "create";
        String input2 = "new task";
        String input3 = "2019-12-31 23:59:59";
        String input4 = "daily";
        String input5 = String.valueOf(device.getId());
        String input6 = "toggle";
        String input7 = "exit";
        MockUtil.mockCommands(input1, input2, input3, input4, input5, input6, input7);
        this.initializeManipulator();

        int sizeBeforeScheduling = taskManager.getAllDevices().size();
        manipulator.run(new String());
        int sizeAfterScheduling = taskManager.getAllDevices().size();

        assertEquals(sizeBeforeScheduling + 1, sizeAfterScheduling);
    }

    @Test
    void runToRemove() {
        String input1 = "remove";
        String input2 = String.valueOf(scheduledTask.getId());
        String input3 = "exit";
        MockUtil.mockCommands(input1, input2, input3);
        this.initializeManipulator();

        int sizeBeforeScheduling = taskManager.getAllDevices().size();
        manipulator.run(new String());
        int sizeAfterScheduling = taskManager.getAllDevices().size();

        assertEquals(sizeBeforeScheduling - 1, sizeAfterScheduling);
    }

    @Test
    void runToRemoveUnexisting() {
        String input1 = "remove";
        String input2 = String.valueOf(Integer.MAX_VALUE);
        String input3 = "exit";
        MockUtil.mockCommands(input1, input2, input3);
        this.initializeManipulator();
        String errorMessage = "Task with id " + input2 + " not found";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setErr(new PrintStream(baos));
        int sizeBeforeScheduling = taskManager.getAllDevices().size();
        manipulator.run(new String());
        int sizeAfterScheduling = taskManager.getAllDevices().size();
        String output = baos.toString();

        assertEquals(sizeBeforeScheduling, sizeAfterScheduling);
        assertTrue(output.contains(errorMessage));
    }

    @Test
    void runToExitWithNoImpact() {
        String input = "exit";

        MockUtil.mockCommands(input);
        this.initializeManipulator();
        int sizeBeforeScheduling = taskManager.getAllDevices().size();
        manipulator.run(new String());
        int sizeAfterScheduling = taskManager.getAllDevices().size();

        assertEquals(sizeBeforeScheduling, sizeAfterScheduling);
    }

    private void initializeManipulator() {
        manipulator = new ScheduleManipulator(new Scanner(System.in), taskManager, deviceManager);
    }
}