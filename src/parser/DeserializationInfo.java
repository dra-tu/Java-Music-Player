package parser;

import java.util.HashMap;

/**
 * The keys of the errors HashMap are the line number from the file in witch the error appeared
 */
public class DeserializationInfo {
    public int totalLineNumber;
    public int readLineNumber;
    public int skippedLineNumber;

    public HashMap<Integer, DeserializationError> errors = new HashMap<>();

    void addError(DeserializationError error) {
        errors.put(totalLineNumber, error);
        skippedLineNumber++;
    }
}
