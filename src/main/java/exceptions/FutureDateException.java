package exceptions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by andre on 6/23/2018.
 */

public class FutureDateException extends IllegalArgumentException {

    public FutureDateException(LocalDate date) {
        super("Date of birth " + date.format(DateTimeFormatter.ofPattern("dd/LL/yyyy")) + " cannot be in the future!");
    }
}

