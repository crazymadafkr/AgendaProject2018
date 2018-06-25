package interfacemenu;

import agendainfo.*;
import enumsandinterfaces.ContactOptions;
import enumsandinterfaces.PhoneType;
import exceptions.InvalidDateException;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by andre on 6/24/2018.
 */

public class Window extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField telephoneNumberTextField;
    private JTextField dateOfBirthTextField;
    private JComboBox<PhoneType> telephoneTypeComboBox;
    private AgendaModel agenda;


    public Window(ContactModel contact, int windowOption) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle(windowOption == ContactOptions.NEW_CONTACT.getValue() ? "New Contact" : "Modify contact");
        setLocation(300, 300);

        setComboBoxModel();

        if (windowOption == ContactOptions.MODIFY_CONTACT.getValue()) {
            updateContactFields(contact);
        }

        buttonOK.addActionListener((e) -> onOK(contact, windowOption));

        buttonCancel.addActionListener((e) -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction((e) -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void onOK(ContactModel contact, int windowOption) {
        try {
            checkNewContactInput();
            TelephoneModel telephone;
            if (windowOption == ContactOptions.NEW_CONTACT.getValue()) {
                if (telephoneTypeComboBox.getModel().getSelectedItem() == PhoneType.Mobile) {
                    telephone = new MobileModel(telephoneNumberTextField.getText());
                } else {
                    telephone = new LandlineModel(telephoneNumberTextField.getText());
                }
                ContactModel newContact = new ContactModel(firstNameTextField.getText(),
                        lastNameTextField.getText(),
                        convertIntoDate(dateOfBirthTextField.getText()),
                        telephone,
                        (PhoneType) telephoneTypeComboBox.getModel().getSelectedItem());
                agenda.addContact(newContact);
            } else {
                contact.setFirstName(firstNameTextField.getText());
                contact.setLastName(lastNameTextField.getText());
                contact.setDateOfBirth(convertIntoDate(dateOfBirthTextField.getText()));
                contact.setTelephoneType((PhoneType) telephoneTypeComboBox.getModel().getSelectedItem());
                TelephoneModel telephoneUpdate = (telephoneTypeComboBox.getModel().getSelectedItem() == PhoneType.Mobile
                        ? new MobileModel(telephoneNumberTextField.getText())
                        : new LandlineModel(telephoneNumberTextField.getText()));
                contact.setTelephoneNumber(telephoneUpdate);
            }
            dispose();
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e.getMessage(), "Invalid data",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        dispose();
    }


    private LocalDate convertIntoDate(String date) {
        String[] splitterDate = date.split("\\/");
        int day = Integer.parseInt(splitterDate[0]);
        int month = Integer.parseInt(splitterDate[1]);
        int year = Integer.parseInt(splitterDate[2]);
        return LocalDate.of(year, month, day);
    }

    private void checkNewContactInput() throws Exception {

        String regex = "\\d{2}\\/\\d{2}\\/\\d{4}";

        if (!dateOfBirthTextField.getText().matches(regex)) {
            throw new InvalidDateException("Please use the format DD/MM/YYYY to insert the date of birth !");
        }
    }

    public void setComboBoxModel() {
        DefaultComboBoxModel<PhoneType> telephoneTypeComboBoxModel = new DefaultComboBoxModel<>();
        for (PhoneType telephoneType : PhoneType.values()
                ) {
            telephoneTypeComboBoxModel.addElement(telephoneType);
        }
        telephoneTypeComboBox.setModel(telephoneTypeComboBoxModel);
    }

    public void updateContactFields(ContactModel contact) {
        firstNameTextField.setText(contact.getFirstName());
        lastNameTextField.setText(contact.getLastName());
        dateOfBirthTextField.setText(contact.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        telephoneNumberTextField.setText(contact.getTelephoneNumber().toString());
        telephoneTypeComboBox.setSelectedItem(contact.getTelephoneType());
    }

    public void setAgenda(AgendaModel agenda) {
        this.agenda = agenda;
    }
}
