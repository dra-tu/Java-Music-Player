package gui.color;

import java.awt.*;

public abstract class JmpGuiColorPalette {
    public JmpGuiColorPalette() {
        System.out.println("Moin from + " + getPaletteName());
    }

    public abstract String getPaletteName();

    public abstract Color getSideBar();
    public abstract Color getBottomBar();
    public abstract Color getMainArea();

    public abstract Color sliderDecoColor();
    public abstract Color thumbColor();
    public abstract Color trackFilledColor();
    public abstract Color trackNotFilledColor();

    /**
     * BaseButton
     * Array structure
     * [state of the button][element]
     * element:
     * - 0 - Background
     * - 1 - Border Color
     * - 2 - Border Highlight
     */
    public abstract Color[][] getBaseButtonColors();
}
