package gui.color.palettes;

import gui.color.JmpGuiColorPalette;

import java.awt.*;

public class NewColorPalette implements JmpGuiColorPalette {
    @Override
    public String getPaletteName() {
        return "New";
    }

    @Override
    public Color getSideBar() {
        return null;
    }

    @Override
    public Color getBottomBar() {
        return null;
    }

    @Override
    public Color getMainArea() {
        return null;
    }
}
