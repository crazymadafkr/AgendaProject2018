package exceptions;

import java.io.IOException;

/**
 * Created by andre on 6/23/2018.
 */

public class InvalidPhoneException extends IOException {
    public InvalidPhoneException(String message) {
        super(message);
    }

}