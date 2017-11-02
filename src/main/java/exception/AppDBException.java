package exception;

public class AppDBException extends Exception {
    public AppDBException() {
    }

    public AppDBException(String message) {
        super(message);
    }
}
