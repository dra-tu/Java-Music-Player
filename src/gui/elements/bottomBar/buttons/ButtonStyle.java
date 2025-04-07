package gui.elements.bottomBar.buttons;

import gui.color.JmpColored;
import gui.color.JmpGuiColorPalette;

import javax.swing.*;
import java.awt.*;

public class ButtonStyle implements JmpColored {
    public ButtonStyle(JButton button, String text) {
        Dimension size = new Dimension(150, 50);

        button.setPreferredSize(size);

        button.setText(text);
    }


    @Override
    public <CP extends JmpGuiColorPalette> void updateColors(CP cp) {

    }
}
