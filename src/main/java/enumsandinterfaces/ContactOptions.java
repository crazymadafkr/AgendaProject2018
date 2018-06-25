package enumsandinterfaces;

/**
 * Created by andre on 6/24/2018.
 */
public enum ContactOptions {
    NEW_CONTACT(1),
    MODIFY_CONTACT(2);

    private final int value;

    ContactOptions(final int i){
        value = i;
    }

    public int getValue(){
        return value;
    }
}
