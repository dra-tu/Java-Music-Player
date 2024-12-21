package parser;

public class FromStringResult<U> {
    boolean worked;
    U obj;

    public FromStringResult(boolean worked, U obj) {
        this.worked = worked;
        this.obj = obj;
    }
}
