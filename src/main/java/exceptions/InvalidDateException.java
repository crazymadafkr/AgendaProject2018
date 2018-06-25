package exceptions;

import java.io.IOException;

/**
 * Created by andre on 6/23/2018.
 */

public class InvalidDateException extends IOException {
    public InvalidDateException(String message) {
        super(message);
    }
}
