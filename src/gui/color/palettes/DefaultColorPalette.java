package gui.color.palettes;

import gui.color.JmpGuiColorPalette;

import java.awt.*;

public class DefaultColorPalette implements JmpGuiColorPalette {
    @Override
    public String getPaletteName() {
        return PALETTE_NAME;
    }

    @Override
    public Color getSideBar() {
        return SIDE_BAR_COLOR;
    }

    @Override
    public Color getBottomBar() {
        return BOTTOM_BAR_COLOR;
    }

    @Override
    public Color getMainArea() {
        return MAIN_AREA_COLOR;
    }

    @Override
    public Color[][] getBaseButtonColors() {
        return BASE_BUTTON_COLORS;
    }

    protected static String PALETTE_NAME = "Default";

    protected static Color SIDE_BAR_COLOR   = new Color(24, 3, 51);
    protected static Color BOTTOM_BAR_COLOR = new Color(33, 18, 50);
    protected static Color MAIN_AREA_COLOR  = new Color(15, 15, 35);

    /**
     * Array structure
     * [state of the button][element]
     * element:
     * - 0 - Background
     * - 1 - Border Color
     * - 2 - Border Highlight
     */
    protected static Color[][] BASE_BUTTON_COLORS = new Color[][] {
            {   // Normal Button
                Color.GREEN,
                Color.BLUE,
                Color.RED
            },
            {   // Song Highlight
                Color.YELLOW,
                Color.green,
                Color.MAGENTA
            }
    };
}
