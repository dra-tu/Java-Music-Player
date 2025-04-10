package gui.elements.jmp;

import gui.color.JmpColored;
import gui.color.JmpGuiColorPalette;
import static gui.elements.jmp.JmpButtonConstant.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class JmpButton extends JButton implements MouseListener, JmpColored {
    private static final Border HOVER_BORDER = BorderFactory.createEmptyBorder();
    private static Color[][] colors;
    private static ArrayList<JmpButton> buttons = new ArrayList<>();

    private Border NORMAL_BORDER;
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        if (state > State.MAX) throw new RuntimeException("Invalid state: " + state);

        this.state = state;
        updateColors();
    }

    public JmpButton() {
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        buttons.add(this);
        addMouseListener(this);

        state = State.NORMAL;
    }

    @Override
    public <CP extends JmpGuiColorPalette> void updateColors(CP cp) {
        colors = cp.getBaseButtonColors();
        updateColors();
    }

    private void updateColors() {
        for (JmpButton button : buttons) {
            button.NORMAL_BORDER = createNormalBorder(colors[button.state][Field.HIGHLIGHT], colors[button.state][Field.BORDER]);
            button.setBorder(button.NORMAL_BORDER);
            button.setBackground(colors[button.state][0]);
        }
    }

    private static Border createNormalBorder(Color highlightColor, Color borderColor) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 3, 0, 0, highlightColor),
                BorderFactory.createMatteBorder(2, 0, 2, 2, borderColor)
        );
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setBorder(HOVER_BORDER);
        setBackground(colors[state][Field.HIGHLIGHT]);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setBorder(NORMAL_BORDER);
        setBackground(colors[state][Field.BACKGROUND]);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // dont care
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // dont care
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // dont care
    }
}