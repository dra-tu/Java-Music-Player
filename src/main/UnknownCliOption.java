package main;

public class UnknownCliOption extends RuntimeException {
    public UnknownCliOption(String strGiven) {
        super("main.UnknownOption: " + strGiven);
    }
}
