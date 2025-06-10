package gui.elements.jmp;

import gui.color.JmpColored;
import gui.color.JmpGuiColorPalette;
import static gui.elements.jmp.JmpButtonConstant.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class JmpButton extends JButton implements MouseListener, JmpColored {
    private static Color[][] colors;

    private Border HOVER_BORDER;
    private Border NORMAL_BORDER;
    private int padding;
    private int state;

    public int getState() {
        return state;
    }

    public void setPadding(int padding) {
        this.padding = padding;
        createHoverBorder();
    }

    public void setState(int state) {
        if (state > State.MAX) throw new RuntimeException("Invalid state: " + state);

        this.state = state;
        updateColors();
    }

    public JmpButton() {
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addMouseListener(this);

        state = State.NORMAL;
        padding = 3;
    }

    @Override
    public <CP extends JmpGuiColorPalette> void updateColors(CP cp) {
        colors = cp.getBaseButtonColors();
        updateColors();
    }

    private void updateColors() {
            updateNormalBorder();
            createHoverBorder();
            setBorder(NORMAL_BORDER);
            setBackground(colors[state][0]);
    }

    private void updateNormalBorder() {
        Border tempBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 3, 0, 0, colors[state][Field.HIGHLIGHT]),
                BorderFactory.createMatteBorder(2, 0, 2, 2, colors[state][Field.BORDER])
        );

        NORMAL_BORDER = BorderFactory.createCompoundBorder(
                tempBorder,
                BorderFactory.createEmptyBorder(padding, padding, padding, padding)
        );
    }

    private void createHoverBorder() {
        Border tempBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 3, 0, 0, colors[state][Field.BORDER]),
                BorderFactory.createMatteBorder(2, 0, 2, 2, colors[state][Field.BACKGROUND])
        );
        HOVER_BORDER = BorderFactory.createCompoundBorder(
                tempBorder,
                BorderFactory.createEmptyBorder(padding, padding+3, padding, padding)
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
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}