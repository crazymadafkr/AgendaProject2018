package agendainfo;

import java.io.Serializable;

/**
 * Created by andre on 6/23/2018.
 */

public abstract class TelephoneModel implements Comparable, Serializable {
    private String phoneNumber;

    public TelephoneModel(String phoneNumber) throws Exception {
        validatePhoneNumber(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    public abstract void validatePhoneNumber(String phoneNumber) throws Exception;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TelephoneModel telephone = (TelephoneModel) o;

        return phoneNumber.equals(telephone.phoneNumber);
    }

    @Override
    public int hashCode() {
        return phoneNumber.hashCode();
    }


    @Override
    public int compareTo(Object o) {
        return Integer.parseInt(this.phoneNumber) - Integer.parseInt(o.toString());
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
