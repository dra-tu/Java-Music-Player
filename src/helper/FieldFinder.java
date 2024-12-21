package helper;

import java.lang.reflect.Field;

public class FieldFinder {
    public static<T> Field findField(Class<T> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class<? super T> superClass = clazz.getSuperclass();
            if (superClass != null) {
                return FieldFinder.findField(superClass, fieldName);
            }else {
                return null;
            }
        }
    }
}
