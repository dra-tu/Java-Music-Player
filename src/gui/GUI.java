package gui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GUI {
    public void start() {
        // Load the image from the JAR file (as a resource)
        URL imgResource = Resources.LOGO.getResource();
        Image img = new ImageIcon(imgResource).getImage();

        icon(img);
        frame(img);
    }

    private void icon(Image img) {
        // official example
//        //Check the SystemTray is supported
//        if (!SystemTray.isSupported()) {
//            System.out.println("SystemTray is not supported");
//            return;
//        }
//        final PopupMenu popup = new PopupMenu();
//        final TrayIcon trayIcon = new TrayIcon(img);
//        final SystemTray tray = SystemTray.getSystemTray();
//
//        // Create a pop-up menu components
//        MenuItem aboutItem = new MenuItem("About");
//        CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
//        CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
//        Menu displayMenu = new Menu("Display");
//        MenuItem errorItem = new MenuItem("Error");
//        MenuItem warningItem = new MenuItem("Warning");
//        MenuItem infoItem = new MenuItem("Info");
//        MenuItem noneItem = new MenuItem("None");
//        MenuItem exitItem = new MenuItem("Exit");
//
//        //Add components to pop-up menu
//        popup.add(aboutItem);
//        popup.addSeparator();
//        popup.add(cb1);
//        popup.add(cb2);
//        popup.addSeparator();
//        popup.add(displayMenu);
//        displayMenu.add(errorItem);
//        displayMenu.add(warningItem);
//        displayMenu.add(infoItem);
//        displayMenu.add(noneItem);
//        popup.add(exitItem);
//
//        trayIcon.setPopupMenu(popup);
//
//        try {
//            tray.add(trayIcon);
//        } catch (AWTException e) {
//            System.out.println("TrayIcon could not be added.");
//        }
//
//        if(!SystemTray.isSupported()){
//            System.out.println("[ERROR] System Tray is not supported");
//            return;
//        }

        final SystemTray tray = SystemTray.getSystemTray();
        final PopupMenu popupMenu = new PopupMenu();
        final TrayIcon icon = new TrayIcon(img);

        icon.setImageAutoSize(true);

        MenuItem menuItem1 = new MenuItem("Hello TEST");
        menuItem1.addActionListener(_ -> System.out.println("Menu item clicked!"));
        menuItem1.addActionListener(_ -> System.out.print("hello"));
        popupMenu.add(menuItem1);

        MenuItem menuItem2 = new MenuItem("2");
        popupMenu.add(menuItem2);

        icon.setPopupMenu(popupMenu);

        try {
            tray.add(icon);
        } catch (AWTException e) {
            System.out.println("[ERROR] Desktop system tray is missing");
        }
    }

    private void frame(Image img) {
        JFrame frame = new JFrame("FrameDemo");
        frame.setSize(500, 320);
        frame.setIconImage(img);
        frame.setVisible(true);
    }
}
