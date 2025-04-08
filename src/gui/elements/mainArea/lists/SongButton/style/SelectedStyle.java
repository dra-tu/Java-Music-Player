package gui.elements.mainArea.lists.SongButton.style;

import gui.color.JmpColored;
import gui.color.JmpGuiColorPalette;

import javax.swing.*;
import java.awt.*;

public class SelectedStyle implements JmpColored {

    private static Color selectedBackground;
    private static Color normalBackground;

    static public <C extends JComponent> void applyStyle(JComponent component) {
        JButton button = (JButton) component;
        button.setBackground(selectedBackground);
    }

    static public <C extends JComponent> void removeStyle(JComponent component) {
        JButton button = (JButton) component;
        button.setBackground(normalBackground);
    }

    @Override
    public <CP extends JmpGuiColorPalette> void updateColors(CP cp) {
        System.out.println("chande color pallet - Button");
        selectedBackground = cp.getButtonSelected();
        normalBackground = cp.getButtonNormal();
    }
}
