package view;

public class InvalidUserInput extends Exception {
    InvalidUserInput(String wrong_input, int input, Throwable cause) {
        super(wrong_input + ": " + input, cause);
    }
}
