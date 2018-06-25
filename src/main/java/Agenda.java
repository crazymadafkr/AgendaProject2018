import agendainfo.AgendaModel;
import agendainfo.ContactModel;
import com.intellij.uiDesigner.core.GridConstraints;
import enumsandinterfaces.*;

import enumsandinterfaces.UnregistredAds;
import filepack.*;
import interfacemenu.*;
import interfacemenu.Window;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

import static agendainfo.ActivationInfo.verifyActivationKey;

/**
 * Created by andre on 6/23/2018.
 */
//
public class Agenda {
    private static FileModel fileMenuForm = new FileModel();
    private static AdsModel adsObject = new AdsModel();
    private static DefaultListModel<ContactModel> contactsListModel;
    private static JFrame frame;
    private JPanel mainPanel;
    private JComboBox<String> filterComboBox;
    private JTextField customFilterTextField;
    private String customFilterText;
    private JComboBox<String> orderComboBox;
    private JButton addContactButton;
    private JButton editContactButton;
    private JButton deleteContactButton;
    private JScrollPane contactsScrollPane;
    private JList<ContactModel> allContacts;
    private JCheckBox orderDescendingCheckBox;
    private OrderType orderCriteria;

    private AgendaModel agenda = new AgendaModel();

    private Agenda(){
        contactsListModel = new DefaultListModel<>();
        allContacts.setModel(contactsListModel);
        customFilterTextField.setEnabled(false);

        setFilterComboBoxModel();
        setOrderComboBoxModel();
        orderContacts();

        filterComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    filterContacts();
                    customFilterTextField.setText("");
                    customFilterText = "";
                    if (FilterType.fromString(filterComboBox.getModel().getSelectedItem().toString()) == FilterType.CUSTOM) {
                        customFilterTextField.setEnabled(true);
                    } else {
                        customFilterTextField.setEnabled(false);
                    }
                    refreshModel();
                }
            }
        });
        customFilterTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                customFilterText = customFilterTextField.getText();
                filterContacts();
                refreshModel();
            }
        });
        orderComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                orderContacts();
            }
        });
        orderDescendingCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                orderContacts();
            }
        });

        FileModel.getExitAgenda().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (confirmExitApplication() == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        FileModel.getRegisterAgenda().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputActivationKey = JOptionPane.showInputDialog("Enter activation key:");
                if (verifyActivationKey(inputActivationKey)) {
                    UnregistredAds.setShowAds(false);
                    JOptionPane.showConfirmDialog(null, "Product is now active !", "Activation OK",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showConfirmDialog(null, "Activation key is incorrect !", "Invalid key",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        FileModel.getAboutAgenda().addActionListener(e ->
                JOptionPane.showConfirmDialog(null, "admin", "Activation key",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE));
        FileModel.getOpenOption().addActionListener(e -> {
            FileMenu.setAgenda(agenda);
            FileMenu.openFile();
            refreshModel();
        });
        FileModel.getSaveOption().addActionListener(e -> {
            FileMenu.setAgenda(agenda);
            FileMenu.saveFile();
            startAutoSaving();
        });

        addContactButton.addActionListener(e -> contactWindow(null, ContactOptions.NEW_CONTACT.getValue()));
        editContactButton.addActionListener(e -> contactWindow(allContacts.getSelectedValue(), ContactOptions.MODIFY_CONTACT.getValue()));
        allContacts.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.getClickCount() == 2) {
                    contactWindow(allContacts.getSelectedValue(), ContactOptions.MODIFY_CONTACT.getValue());
                }
            }
        });
        deleteContactButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ContactModel contact = allContacts.getSelectedValue();
                if (contact == null) {
                    JOptionPane.showConfirmDialog(null, "Please select a contact from the list !", "Invalid selection",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                } else {
                    int dialogOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove " +
                                    contact.getFirstName() + " " +
                                    contact.getLastName() + " from the list ?", "Confirm delete",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (dialogOption == JOptionPane.YES_OPTION) {
                        agenda.removeContact(contact);
                        refreshModel();
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        adsObject = new AdsModel();
        adsObject.addAdsPictureToPanel();
        frame = new JFrame("Agenda");
        frame.setContentPane(new Agenda().mainPanel);
        frame.setJMenuBar(FileModel.getMenuOptionsBar());
        frame.add(adsObject, new GridConstraints(4, 0, 1, 4, 0, 3, 1, 4, new Dimension(-1, 100), new Dimension(-1, 100), new Dimension(-1, 100)));
        frame.setLocation(200, 200);
        frame.pack();
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                if (confirmExitApplication() == JOptionPane.YES_OPTION) {
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                } else {
                    frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        if (UnregistredAds.isShowAds()) {
            Thread ads = new Thread(new UnregistredAds(), "Thread-ads happening");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ads.start();
            UnregistredAds.enableFiles(!UnregistredAds.isShowAds());
        }

        removeAdsFormTimeTask();
    }

    private static int confirmExitApplication() {
        return JOptionPane.showConfirmDialog(null, "Are you sure you want to exit ?", "Exit",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    private static void removeAdsFormTimeTask() {
        TimerTask removeAdsForm = new TimerTask() {
            @Override
            public void run() {
                if (!UnregistredAds.isShowAds()) {
                    frame.remove(adsObject);
                    frame.pack();
                    cancel();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(removeAdsForm, 1000, 1000);
    }

    private void refreshModel() {
        contactsListModel.clear();
        if (agenda.getContacts() != null && agenda.getContacts().size() > 0) {
            if (orderDescendingCheckBox.isSelected()) {
                agenda.getContacts()
                        .stream()
                        .filter(agenda.getFilterCriteria())
                        .sorted(agenda.setAndGetCriteriaToComparator(orderCriteria).reversed())
                        .forEach(contactsListModel::addElement);
            } else {
                agenda.getContacts()
                        .stream()
                        .filter(agenda.getFilterCriteria())
                        .sorted(agenda.setAndGetCriteriaToComparator(orderCriteria))
                        .forEach(contactsListModel::addElement);
            }
        }
    }

    private void contactWindow(ContactModel contact, int windowType) {
        if (windowType == ContactOptions.MODIFY_CONTACT.getValue() && contact == null) {
            JOptionPane.showConfirmDialog(null, "Please select a contact from the list !", "Invalid selection",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        } else {
            Window contactWindow = new Window(contact, windowType);
            contactWindow.setAgenda(agenda);
            contactWindow.pack();
            contactWindow.setVisible(true);
            refreshModel();
        }
    }

    private void setFilterComboBoxModel() {
        DefaultComboBoxModel<String> filterCriteriaComboBoxModel = new DefaultComboBoxModel<>();
        for (OrderType filterType : OrderType.values()
                ) {
            filterCriteriaComboBoxModel.addElement(OrderType.enumToText(filterType));
        }
        filterComboBox.setModel(filterCriteriaComboBoxModel);
    }

    private void setOrderComboBoxModel() {
        DefaultComboBoxModel<String> orderCriteriaComboBoxModel = new DefaultComboBoxModel<>();
        for (OrderType orderCriteria : OrderType.values()
                ) {
            orderCriteriaComboBoxModel.addElement(OrderType.enumToText(orderCriteria));
        }
        orderComboBox.setModel(orderCriteriaComboBoxModel);
    }

    private void orderContacts() {
        orderCriteria = OrderType.fromString(orderComboBox.getModel().getSelectedItem().toString());
        refreshModel();
    }

    private void filterContacts() {
        switch (FilterType.fromString(filterComboBox.getModel().getSelectedItem().toString())) {
            case MOBILE_TELEPHONE:
                agenda.filterMobileTelephones();
                break;
            case LANDLINE_TELEPHONE:
                agenda.filterLandlineTelephone();
                break;
            case BORN_CURRENT_MONTH:
                agenda.filterBornThisMonth();
                break;
            case BORN_TODAY:
                agenda.filterBornToday();
                break;
            case CUSTOM:
                if (customFilterText != null && customFilterText.length() > 0) {
                    agenda.filterOnCustomFilter(customFilterText);
                } else {
                    agenda.filterNothing();
                }
                break;
            case ALL:
            default:
                agenda.filterNothing();
        }
    }

    private void startAutoSaving() {
        TimerTask autoSaving = new TimerTask() {
            @Override
            public void run() {
                FileMenu.autoSave(agenda);
            }
        };
        Timer timer = new Timer();
        timer.schedule(autoSaving, 90000, 90000);
    }
}

