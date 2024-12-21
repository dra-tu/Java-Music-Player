package musicPlayer.configTypes;

import parser.Parser;

import static helper.FieldFinder.findField;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

public class FileSaver {
    protected final File saveFile;

    protected static Field[] ignoreFields;

    static {
        Field[] temp;
        try {
            temp = new Field[]{
                    FileSaver.class.getDeclaredField("saveFile"),
                    FileSaver.class.getDeclaredField("ignoreFields")
            };
        } catch (NoSuchFieldException ignored) {
            temp = new Field[0];
        }
        ignoreFields = temp;
    }
    protected static void addToIgnoreFields(Field[] newFields) {
        ignoreFields = Stream
                .concat(Arrays.stream(newFields), Arrays.stream(FileSaver.ignoreFields))
                .toArray(size -> (Field[]) Array.newInstance(Field.class, size));
    }

    public File getSaveFile() {
        return saveFile;
    }

    protected FileSaver(File saveFile) throws FileSaverException, IOException {
        if (saveFile.isDirectory())
            throw new IOException(saveFile.getAbsolutePath() + " is a Directory");

        boolean fileCreated = saveFile.createNewFile();

        this.saveFile = saveFile;
        if (fileCreated) {
            Parser.writeToFile(saveFile, ignoreFields, this);
        } else {
            Parser.readToObject(saveFile, ignoreFields, this);
        }
    }

    <T> boolean updateField(String fieldName, Check<T> checkFunction, T newValue) throws IOException {
        if (!checkFunction.isValid(newValue)) return false;

        Field field = findField(getClass(), fieldName);
        if (field == null) return false;

        try {
            field.setAccessible(true);
            Object old = field.get(this);
            field.set(this, newValue);

            int errors = Parser.writeToFile(saveFile, ignoreFields, this);
            boolean updated = errors <= 0;
            if (!updated) {
                field.set(this, old);
                return false;
            }
            return true;
        } catch (IllegalAccessException e) {
            return false;
        }
    }

    interface Check<T> {
        boolean isValid(T value);
    }
}
