package interfacemenu;

import javax.swing.*;

/**
 * Created by andre on 6/24/2018.
 */
public class AdsModel extends JPanel {
    private static JLabel adsPicture;

    public static void setAdsPictureIcon(ImageIcon imageIcon) {
        adsPicture.setIcon(imageIcon);
    }

    public void addAdsPictureToPanel() {
        adsPicture = new JLabel();
        this.add(adsPicture);
    }
}
