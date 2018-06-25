package exceptions;

import java.io.IOException;

/**
 * Created by andre on 6/23/2018.
 */

public class DuplicateContactException extends IOException {
    public DuplicateContactException(String message) {
        super(message);
    }
}

