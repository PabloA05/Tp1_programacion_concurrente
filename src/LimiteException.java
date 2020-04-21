/**
 * Lo uso para tirar true o false cuando lo necesito reposition or consume
 * */
public class LimiteException extends Exception {
    boolean logic;

    public LimiteException(boolean id) {
        this.logic = id;
    }

    public boolean aBoolean(boolean a) {
        return logic;
    }
}