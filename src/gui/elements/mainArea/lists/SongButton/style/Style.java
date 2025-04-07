package gui.elements.mainArea.lists.SongButton.style;

import javax.swing.*;

public interface Style {
    public<C extends JComponent> void applyStyle(C component);
    public<C extends JComponent> void removeStyle(C component);
}
