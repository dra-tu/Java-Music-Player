package gui.color;

public interface JmpColored {
    <CP extends JmpGuiColorPalette> void updateColors(CP cp);
}
