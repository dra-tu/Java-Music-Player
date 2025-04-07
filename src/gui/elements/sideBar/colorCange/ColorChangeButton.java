package gui.elements.sideBar.colorCange;

import gui.color.ColorMgr;
import javax.swing.*;
import java.awt.*;

public class ColorChangeButton extends JComboBox<String> {
    public ColorChangeButton(ColorMgr mgr) {
        super(ColorMgr.getColorPaletteNames());

        Dimension size = new Dimension(100, 25);
        // setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
        setSize(size);


        addActionListener((event) -> {

            mgr.changeColor((String) getSelectedItem());

            System.out.println("COLOR ACTION: " + getSelectedItem());
        });
    }
}
