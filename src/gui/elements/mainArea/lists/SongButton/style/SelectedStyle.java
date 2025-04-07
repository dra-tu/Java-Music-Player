package gui.elements.mainArea.lists.SongButton.style;

import javax.swing.*;

public class SelectedStyle {

    static public <C extends JComponent> void applyStyle(JComponent component) {
        JButton button = (JButton) component;
        // TODO
        // button.setBackground(new DeafuldColorPalette.getForground());
    }


    static public <C extends JComponent> void removeStyle(JComponent component) {
        JButton button = (JButton) component;
        JButton defaultButton = new JButton();

        button.setBackground(defaultButton.getBackground());
    }
}
