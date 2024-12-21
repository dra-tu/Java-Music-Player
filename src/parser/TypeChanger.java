package parser;

import java.io.File;
import java.util.Map;
import java.util.function.Function;

public class TypeChanger {
    static String nullString = "<NULL>";
    private static final Map<String, Function<String, Object>> TYPE_MAP = Map.of(
            "byte", Byte::valueOf,
            "int", Integer::valueOf,
            "Integer", Integer::valueOf,
            "String", (str) -> str,
            "File", File::new
    );
    private static final Map<Class<?>, Function<Object, String>> String_MAP = Map.of(
            File.class, (Object obj) -> {
                File file = (File) obj;
                return file.getAbsolutePath();
            }
    );

    static <U> String toString(U typeValue) {
        if (typeValue == null) {
            return nullString;
        }
        Function<Object, String> func = String_MAP.getOrDefault(
                typeValue.getClass(),
                Object::toString
        );
        return func.apply(typeValue);
    }

    @SuppressWarnings("unchecked")
    static <U> FromStringResult<U> fromString(String typeName, String sValue) {
        if (typeName == null || sValue == null) return null;

        if (sValue.equals(nullString))
            return new FromStringResult<>(true, null);

        Function<String, Object> func = TYPE_MAP.get(typeName);

        return func == null
                ? new FromStringResult<>(false, null)
                : new FromStringResult<>(true, (U) func.apply(sValue));
    }
}
