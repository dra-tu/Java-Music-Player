package gui.color;

import java.awt.*;

public interface JmpGuiColorPalette {
    String getPaletteName();

    Color getSideBar();
    Color getBottomBar();
    Color getMainArea();

    /**
     * BaseButton
     * Array structure
     * [state of the button][element]
     * element:
     * - 0 - Background
     * - 1 - Border Color
     * - 2 - Border Highlight
     */
    Color[][] getBaseButtonColors();
}
