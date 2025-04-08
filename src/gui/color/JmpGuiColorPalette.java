package gui.color;

import java.awt.*;

public interface JmpGuiColorPalette {
    String getPaletteName();

    Color getSideBar();
    Color getBottomBar();
    Color getMainArea();

    Color getButtonSelected();
    Color getButtonNormal();
}
