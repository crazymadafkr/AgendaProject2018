package agendainfo;

import enumsandinterfaces.PhoneType;
import exceptions.FutureDateException;
import exceptions.InvalidContactException;

import java.io.Serializable;
import java.time.LocalDate;

import static enumsandinterfaces.PhoneType.Mobile;

/**
 * Created by andre on 6/23/2018.
 */

public class ContactModel implements Serializable {

    private String firstName;
    private String lastName;
    private Enum<PhoneType> telephoneType;
    private LocalDate dateOfBirth;
    private TelephoneModel telephoneNumber;

    public ContactModel(String firstName, String lastName, LocalDate dateOfBirth, TelephoneModel telephoneNumber, Enum<PhoneType> telephoneType) throws Exception {
        inputValidation(firstName, lastName, dateOfBirth, telephoneType);

        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.telephoneNumber = telephoneNumber;
        this.telephoneType = telephoneType;
    }

    private void inputValidation(String firstName, String lastName, LocalDate dateOfBirth, Enum<PhoneType> telephoneType) throws Exception {
        firstNameVerification(firstName);
        lastNameVerification(lastName);
        dateOfBirthVerification(dateOfBirth);
    }

    private void firstNameVerification(String firstName) throws Exception {
        if (firstName == null || firstName.length() < 2) {
            throw new InvalidContactException("First name needs to have at least 2 characters!");
        }
        if (!firstName.matches("^[\\p{L} .'-]+$")) {
            throw new InvalidContactException("First name needs to contain letters, spaces or separators !");
        }
    }

    private void lastNameVerification(String lastName) throws Exception {
        if (lastName == null || lastName.length() < 2) {
            throw new InvalidContactException("Last name needs to have at least 2 characters !");
        }
        if (!lastName.matches("^[\\p{L} .'-]+$")) {
            throw new InvalidContactException("Last name needs to contain letters, spaces or separators !");
        }
    }

    private void dateOfBirthVerification(LocalDate dateOfBirth) throws Exception {
        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new FutureDateException(dateOfBirth);
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws Exception {
        firstNameVerification(firstName);
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws Exception {
        lastNameVerification(lastName);
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) throws Exception {
        dateOfBirthVerification(dateOfBirth);
        this.dateOfBirth = dateOfBirth;
    }

    public Enum<PhoneType> getTelephoneType() {
        return telephoneType;
    }

    public void setTelephoneType(Enum<PhoneType> telephoneType){
        this.telephoneType = telephoneType;
    }

    public TelephoneModel getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(TelephoneModel telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    @Override
    public String toString() {
        return firstName + ' ' +
                lastName + " born on " +
                dateOfBirth + ". " +
                (telephoneType == Mobile ? "Mobile" : "Landline") +
                " number : " +
                telephoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactModel contact = (ContactModel) o;

        if (!firstName.equals(contact.firstName)) return false;
        if (!lastName.equals(contact.lastName)) return false;
        if (!dateOfBirth.equals(contact.dateOfBirth)) return false;
        if (!telephoneType.equals(contact.telephoneType)) return false;
        return telephoneNumber.equals(contact.telephoneNumber);

    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + dateOfBirth.hashCode();
        result = 31 * result + telephoneType.hashCode();
        result = 31 * result + telephoneNumber.hashCode();
        return result;
    }
}
