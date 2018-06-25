package agendainfo;

/**
 * Created by andre on 6/23/2018.
 */
public class ActivationInfo {
    private static final String ACTIVATION_KEY = "admin1";

    public static boolean verifyActivationKey(String activationKey) {
        return activationKey.toLowerCase().equals(ACTIVATION_KEY.toLowerCase());
    }
}