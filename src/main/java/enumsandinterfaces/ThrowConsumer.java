package enumsandinterfaces;

import java.io.File;
import java.io.IOException;

/**
 * Created by andre on 6/24/2018.
 */

@FunctionalInterface
public interface ThrowConsumer<T extends File> {

    void accept(final T e) throws IOException, ClassNotFoundException;

}