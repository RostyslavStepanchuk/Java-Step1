package view;

import java.text.ParseException;
import java.util.InputMismatchException;

public class InvalidUserInput extends Exception {
    public InvalidUserInput(String wrong_input, int input, Throwable cause) {
        super(wrong_input + ": " + input, cause);
    }
}
