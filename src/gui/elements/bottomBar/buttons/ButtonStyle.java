package gui.elements.bottomBar.buttons;

import javax.swing.*;
import java.awt.*;

public class ButtonStyle {
    public ButtonStyle(JButton button, String text) {
         Dimension size = new Dimension(150, 50);

        button.setPreferredSize(size);

        button.setText(text);
    }
}