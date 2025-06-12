package gui.color;

import java.util.*;

public class ColorMgr {
    private ArrayList<JmpColored> elements;
    private JmpGuiColorPalette curedPallet;

    public final ArrayList<String> COLOR_PALETTE_NAMES;
    private final Map<String, JmpGuiColorPalette> MAP;

    public ColorMgr() {
        int cap = 10;

        elements = new ArrayList<>();
        COLOR_PALETTE_NAMES = new ArrayList<>(cap);
        MAP = new HashMap<>(cap);
    }
    public void removeAll() {
        elements = new ArrayList<>();
    }

    public void add(JmpColored element) {
        elements.add(element);

        if (curedPallet == null) return;
        element.updateColors(curedPallet);
    }

    public Object getSelectedPaletteName() {
        return curedPallet.getPaletteName();
    }

    public void changeColor(String str) {
        JmpGuiColorPalette palette = MAP.get(str);
        if (palette == null) return;
        changeColor(palette);
    }

    public void addPaletteToList(JmpGuiColorPalette palette) {
        if (palette == null) return;

        JmpGuiColorPalette oldPalette = MAP.put(palette.getPaletteName(), palette);
        if (oldPalette == null) {
            COLOR_PALETTE_NAMES.add(palette.getPaletteName());
        }
    }

    public <CP extends JmpGuiColorPalette> void changeColor(CP colorPalette) {
        curedPallet = colorPalette;

        for (JmpColored element : elements) {
            element.updateColors(colorPalette);
        }
    }

    public String[] getColorPaletteNames() {
        return COLOR_PALETTE_NAMES.toArray(new String[0]);
    }
}
