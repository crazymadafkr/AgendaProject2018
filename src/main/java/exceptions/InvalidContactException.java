package exceptions;

/**
 * Created by andre on 6/23/2018.
 */

public class InvalidContactException extends IllegalArgumentException {
    public InvalidContactException(String message) {
        super(message);
    }
}

