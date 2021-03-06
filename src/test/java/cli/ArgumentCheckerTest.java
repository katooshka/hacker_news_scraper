package cli;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static cli.ArgumentsReader.readArguments;

public class ArgumentCheckerTest {

    @Test
    public void readArguments_shouldThrow_whenWrongArgumentsNumber() {
        try {
            readArguments(new String[]{"a", "b", "c"});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Wrong number of arguments", e.getMessage());
        }
    }

    @Test
    public void readArguments_shouldThrow_whenWrongFirstArgument() {
        try {
            readArguments(new String[]{"abc", "90"});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Wrong first argument", e.getMessage());
        }
    }

    @Test
    public void readArguments_shouldThrow_whenSecondArgumentIsNotNumber() {
        try {
            readArguments(new String[]{"--posts", "abc"});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Second argument is not a number", e.getMessage());
        }
    }

    @Test
    public void readArguments_shouldThrow_whenSecondArgumentIsNegative() {
        try {
            readArguments(new String[]{"--posts", "-1"});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Second argument is a negative number", e.getMessage());
        }
    }

    @Test
    public void readArguments_shouldThrow_whenSecondArgumentIsZero() {
        try {
            readArguments(new String[]{"--posts", "0"});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Second argument is zero", e.getMessage());
        }
    }

    @Test
    public void readArguments_shouldThrow_whenSecondArgumentIsGreaterThanMaxPostsNumber() {
        try {
            readArguments(new String[]{"--posts", "101"});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Second argument is greater than 100", e.getMessage());
        }
    }

    @Test
    public void readArguments_shouldReturnSecondArgument() {
        assertEquals(5, readArguments(new String[]{"--posts", "5"}));
    }
}
