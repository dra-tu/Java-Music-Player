package musicPlayer.parser;

import static musicPlayer.parser.FieldFinder.findField;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    final static String FORMAT_PATTERN = "[%s] %s : %s%n";
    final static Pattern PATTERN = Pattern.compile("\\[(\\S+)]\\s(\\S+) : (.+)");

    /**
     * @param file The file to read from
     * @param obj The Object to insert values found in the file
     * @return A DeserializationInfo obj - It has info about errors, line count, ...
     * @param <T> The type of obj
     * @param <U> For internal use [ Type to save field values ]
     * @throws IOException If an I/ O error occurs
     */
    public static <T, U> DeserializationInfo readToObject(File file, Field[] ignoreFields, T obj) throws IOException {
        DeserializationInfo info = new DeserializationInfo();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                info.totalLineNumber++;
                Matcher matcher = PATTERN.matcher(line);

                if (matcher.find()) {
                    String typeName = matcher.group(1);
                    String fieldName = matcher.group(2);
                    String sValue = matcher.group(3);

                    FromStringResult<U> stringResult = TypeChanger.fromString(typeName, sValue);
                    Field field = findField(obj.getClass(), fieldName);
                    if (!stringResult.worked) {
                        info.addError(DeserializationError.UNSUPPORTED_TYPE);
                        continue;
                    }
                    if (field == null) {
                        info.addError(DeserializationError.UNKNOWN_FIELD);
                        continue;
                    }
                    if (Arrays.asList(ignoreFields).contains(field)) {
                        continue;
                    }

                    try {
                        field.setAccessible(true);
                        field.set(obj, stringResult.obj);
                    } catch (IllegalAccessException e) {
                        info.addError(DeserializationError.ILLEGAL_FIELD_ACCESS);
                        continue;
                    }
                    info.readLineNumber++;
                }else {
                    info.addError(DeserializationError.INVALID_LINE_FORMAT);
                }
            }
        }
        return info;
    }

    /**
     * @param file The file to overwrite
     * @param obj The object of which the fields will be saved in filePath
     * @return number of Illegal Accesses to a Field - so the number of skipped Fields
     * @param <T> The type of obj
     * @throws IOException if the named file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason
     */
    public static <T> int writeToFile(File file, Field[] ignoreFields, T obj) throws IOException {
        Class<?> clazz = obj.getClass();
        int numIllegalAccesses = 0;

        try (FileWriter writer = new FileWriter(file)) {
            while (clazz != null) {
                for (Field field : clazz.getDeclaredFields()) {

                    if (Arrays.asList(ignoreFields).contains(field)) {
                        continue;
                    }

                    try {
                        field.setAccessible(true);
                        String typeName = field.getType().getSimpleName();
                        String fieldName = field.getName();
                        String sValue = TypeChanger.toString(field.get(obj));

                        writer.write(String.format(FORMAT_PATTERN, typeName, fieldName, sValue));
                    } catch (IllegalAccessException e) {
                        numIllegalAccesses++;
                    }
                }
                clazz = clazz.getSuperclass();
            }
        }
        return numIllegalAccesses;
    }
}
