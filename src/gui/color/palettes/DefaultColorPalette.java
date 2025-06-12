package gui.color.palettes;

import gui.color.JmpGuiColorPalette;

import java.awt.*;

public class DefaultColorPalette implements JmpGuiColorPalette {
    @Override
    public String getPaletteName() {
        return "Default";
    }

    @Override
    public Color getSideBar() {
        return new Color(24, 3, 51);
    }

    @Override
    public Color getBottomBar() {
        return new Color(33, 18, 50);
    }

    @Override
    public Color getMainArea() {
        return new Color(15, 15, 35);
    }

    @Override
    public Color sliderDecoColor() {
        return Color.BLACK;
    }

    @Override
    public Color thumbColor() {
        return Color.BLUE;
    }

    @Override
    public Color trackFilledColor() {
        return Color.MAGENTA;
    }

    @Override
    public Color trackNotFilledColor() {
        return Color.GRAY;
    }

    @Override
    public Color[][] getBaseButtonColors() {
        return new Color[][]{
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
}
