package filepack;

import agendainfo.AgendaModel;
import agendainfo.ContactModel;
import enumsandinterfaces.ThrowConsumer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Set;

/**
 * Created by andre on 6/24/2018.
 */

public class FileMenu extends JFileChooser {
    private static File inputFile;
    private static AgendaModel agenda;

    public static void saveFile() {
        JFileChooser fileChooser = createFile();
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            inputFile = fileChooser.getSelectedFile();
            if (inputFile.exists()) {
                if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null,
                                            "Do you want to overwrite "
                                             + inputFile.getAbsolutePath()
                                                    + "?", "Confirm Save",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) ;
            }
            fileExceptionHandlers(inputFile, file -> writeToFile(inputFile));
        }
    }

    public static void openFile() {
        JFileChooser fileChooser = createFile();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            inputFile = fileChooser.getSelectedFile();
            fileExceptionHandlers(inputFile, file -> readFromFile(inputFile));
        }
    }

    private static void fileExceptionHandlers(File file, ThrowConsumer fileWork) {
        try {
            fileWork.accept(file);
        } catch (FileNotFoundException e) {
            JOptionPane.showConfirmDialog(null, "File " + file.getAbsoluteFile() + " not found !", "Error",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(null, "Not enough rights over file " + file.getAbsoluteFile(), "Error",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showConfirmDialog(null, "Big error " + file.getAbsoluteFile(), "Error",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void writeToFile(File selectedFile) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(selectedFile.getAbsolutePath());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(agenda.getContacts());
        objectOutputStream.close();
    }

    private static void readFromFile(File selectedFile) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(selectedFile.getAbsolutePath());
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Set<ContactModel> contactsRead = (Set<ContactModel>) objectInputStream.readObject();
        objectInputStream.close();
        agenda.renewContacts(contactsRead);
    }

    private static JFileChooser createFile() {
        javax.swing.filechooser.FileFilter fileFilter = new FileNameExtensionFilter("agenda", "txt");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(fileFilter);
        return fileChooser;
    }

    public static void setAgenda(AgendaModel agenda) {
        FileMenu.agenda = agenda;
    }

    public static void autoSave(AgendaModel agenda) {
        setAgenda(agenda);
        fileExceptionHandlers(inputFile, file -> writeToFile(inputFile));
    }
}
