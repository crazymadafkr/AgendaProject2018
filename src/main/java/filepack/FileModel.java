package filepack;

import javax.swing.*;

/**
 * Created by andre on 6/24/2018.
 */

public class FileModel {
    private static JMenuBar menuOptionsBar;
    private static JMenu fileOption;
    private static JMenu helpOption;
    private static JMenuItem exitAgenda;
    private static JMenuItem registerAgenda;
    private static JMenuItem aboutAgenda;
    private static JMenuItem openOption;
    private static JMenuItem saveOption;


    public FileModel() {
        menuOptionsBar = new JMenuBar();
        fileOption = new JMenu("File");
        helpOption = new JMenu("Help");
        menuOptionsBar.add(fileOption);
        menuOptionsBar.add(helpOption);
        registerAgenda = new JMenuItem("Register");
        aboutAgenda = new JMenuItem("Activation Key");
        openOption = new JMenuItem("Open File");
        saveOption = new JMenuItem("Save File");
        exitAgenda = new JMenuItem("Exit");


        fileOption.add(openOption);
        fileOption.add(saveOption);
        fileOption.add(exitAgenda);
        helpOption.add(registerAgenda);
        helpOption.add(aboutAgenda);
    }

    public static JMenuBar getMenuOptionsBar() {
        return menuOptionsBar;
    }

    public static JMenuItem getExitAgenda() {
        return exitAgenda;
    }

    public static JMenuItem getRegisterAgenda() {
        return registerAgenda;
    }

    public static JMenuItem getAboutAgenda() {
        return aboutAgenda;
    }

    public static JMenuItem getOpenOption() {
        return openOption;
    }

    public static JMenuItem getSaveOption() {
        return saveOption;
    }

    public static void enableOpenFile(boolean setterEnabled) {
        openOption.setEnabled(setterEnabled);
    }

    public static void enableSaveFile(boolean setterEnabled) {
        saveOption.setEnabled(setterEnabled);
    }
}