package gui.color;

import java.awt.*;

public interface JmpGuiColorPalette {
    String getPaletteName();

    Color getSideBar();
    Color getBottomBar();
    Color getMainArea();

    Color sliderDecoColor();
    Color thumbColor();
    Color trackFilledColor();
    Color trackNotFilledColor();

   /**
     * Array structure:<br>
     * [state of the button][element]<br>
     * element:<br>
     * - 0 - Background<br>
     * - 1 - Border Color<br>
     * - 2 - Border Highlight
     */
   Color[][] getBaseButtonColors();
}
