package agendainfo;

import exceptions.InvalidPhoneException;

import java.io.Serializable;

/**
 * Created by andre on 6/23/2018.
 */
public class MobileModel extends TelephoneModel implements Serializable {

    public MobileModel(String phoneNumber) throws Exception {
        super(phoneNumber);
        validatePhoneNumber(phoneNumber);
    }

    @Override
    public void validatePhoneNumber(String phoneNumber) throws Exception {
        if (phoneNumber != null && phoneNumber.length() != 10) {
            throw new InvalidPhoneException("Mobile number must have 10 digits !");
        }
        if (phoneNumber != null && !phoneNumber.matches("\\d{10}")) {
            throw new InvalidPhoneException("Mobile number must contain only digits !");
        }
        if (phoneNumber != null && !phoneNumber.startsWith("07")) {
            throw new InvalidPhoneException("Mobile phone numbers must start with 07 !");
        }
    }

    @Override
    public int compareTo(Object o) {
        return Integer.parseInt(getPhoneNumber()) - Integer.parseInt(o.toString());
    }

    @Override
    public String toString() {
        return getPhoneNumber();
    }

    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    public void setPhoneNumber(String phoneNumber) {
        super.setPhoneNumber(phoneNumber);
    }
}
