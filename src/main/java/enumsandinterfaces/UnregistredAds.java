package enumsandinterfaces;

import filepack.FileModel;
import interfacemenu.AdsModel;

import javax.swing.*;
import java.io.File;

/**
 * Created by andre on 6/24/2018.
 */

public class UnregistredAds extends Thread {
    private static boolean showAds = true;
    private static ImageIcon banner;
    private static File adsFilePath = new File(File.separator
                                             + "src" + File.separator
                                             + "main" + File.separator
                                             + "java" + File.separator
                                             + "interfacemenu" + File.separator
                                             + "pictures");
    private File[] ads;
    private int currentBannerPosition = 0;
    private int previousBannerPosition = 0;

    public static boolean isShowAds() {
        return showAds;
    }

    public static void setShowAds(boolean showAds) {
        UnregistredAds.showAds = showAds;
    }

    private static File[] getBannersPaths() {
        File directory = new File(new File("").getAbsolutePath() + adsFilePath);
        return directory.listFiles(f -> f.getName().toLowerCase().endsWith(".jpg"));
    }

    public static void enableFiles(boolean doEnableFiles) {
        FileModel.enableOpenFile(doEnableFiles);
        FileModel.enableSaveFile(doEnableFiles);
    }

    public void run() {
        ads = getBannersPaths();

        while (showAds) {
            while (currentBannerPosition == previousBannerPosition) {
                currentBannerPosition = (int) (ads.length * Math.random());
            }
            previousBannerPosition = currentBannerPosition;
            banner = new ImageIcon(ads[currentBannerPosition].toString());

            AdsModel.setAdsPictureIcon(banner);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        AdsModel.setAdsPictureIcon(new ImageIcon(""));
        enableFiles(!showAds);
    }
}

