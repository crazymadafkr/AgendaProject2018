package agendainfo;

import enumsandinterfaces.OrderType;
import enumsandinterfaces.PhoneType;
import exceptions.DuplicateContactException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

import static enumsandinterfaces.OrderType.*;

/**
 * Created by andre on 6/23/2018.
 */
public class AgendaModel implements Serializable {
    private Set<ContactModel> allContacts = new HashSet<>();

    private Map<OrderType, Comparator> orderCriteria = new HashMap<>();
    private Comparator<ContactModel> criteriaComparator;
    private Predicate<ContactModel> filterType = contact -> true;


    public AgendaModel() {
        orderCriteria.put(FIRSTNAME, Comparator.comparing(ContactModel::getFirstName));
        orderCriteria.put(LASTNAME, Comparator.comparing(ContactModel::getLastName));
        orderCriteria.put(TELEPHONE, Comparator.comparing(ContactModel::getTelephoneNumber));
        orderCriteria.put(BIRTHDATE, Comparator.comparing(ContactModel::getDateOfBirth));
    }

    public void addContact(ContactModel contact) throws Exception {
        if (!allContacts.add(contact)) {
            throw new DuplicateContactException("Duplicate contact !");
        }
    }

    public void removeContact(ContactModel contact) {
        allContacts.remove(contact);
    }

    private void removeAllContacts() {
        allContacts.clear();
    }

    public Set<ContactModel> getContacts() {
        return allContacts;
    }

    public void renewContacts(Set<ContactModel> contactsList) {
        removeAllContacts();
        allContacts.addAll(contactsList);
    }

    public void filterLandlineTelephone() {
        filterType = contact -> PhoneType.Landline.equals(contact.getTelephoneType());
    }

    public void filterMobileTelephones() {
        filterType = contact -> PhoneType.Mobile.equals(contact.getTelephoneType());
    } public void filterBornToday() {
        filterType = contact -> contact.getDateOfBirth().isEqual(LocalDate.now());
    }

    public void filterBornThisMonth() {
        filterType = contact -> contact.getDateOfBirth().getMonth().equals(LocalDate.now().getMonth());
    }

    public void filterOnCustomFilter(String filter) {
        filterType = contact -> contact.getFirstName().toLowerCase().contains(filter.toLowerCase())
                || contact.getLastName().toLowerCase().contains(filter.toLowerCase())
                || contact.getTelephoneNumber().toString().contains(filter);
    }

    public void filterNothing() {
        filterType = contact -> true;
    }

    public Predicate<ContactModel> getFilterCriteria() {
        return filterType;
    }

    public Comparator<ContactModel> setAndGetCriteriaToComparator(OrderType orderType) {
        return criteriaComparator = orderCriteria.getOrDefault(orderType, Comparator.comparing(ContactModel::getFirstName));
    }

}
