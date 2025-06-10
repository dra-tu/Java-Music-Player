package gui.elements.jmp;

import gui.color.ColorMgr;
import gui.color.JmpColored;
import gui.color.JmpGuiColorPalette;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public class JmpSlider extends JSlider {
    private static final float TRACK_THICKNESS = 0.5F;

    public JmpSlider(ColorMgr colorMgr, int min, int max) {
        super(min, max);
        JmpSliderUI ui = new JmpSliderUI();
        colorMgr.add(ui);
        this.setUI(ui);
    }

    private static class JmpSliderUI extends BasicSliderUI implements JmpColored {
        private Color decoColor;
        private Color thumbColor;
        private Color trackFilledColor;
        private Color trackNotFilledColor;

        @Override
        public void paintThumb(Graphics g) {
            g.setColor(thumbColor);
            g.fillRect(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
        }

        @Override
        public void paintTrack(Graphics g) {
            if (slider.getMaximum() == 0) return;
            float fillPercent = (float) slider.getValue() / slider.getMaximum();

            if (slider.getOrientation() == JSlider.HORIZONTAL) {
                int delta_width = (int) (trackRect.width * fillPercent);
                int height = (int) (trackRect.height * TRACK_THICKNESS);
                int y = trackRect.y + ((trackRect.height - height) / 2);

                g.setColor(trackFilledColor);
                g.fillRect(trackRect.x, y, delta_width, height);

                g.setColor(trackNotFilledColor);
                g.fillRect(delta_width, y, trackRect.width - delta_width, height);
            } else {
                int delta_height = (int) (trackRect.height * fillPercent);
                int width = (int) (trackRect.width * TRACK_THICKNESS);
                int x = trackRect.x + ((trackRect.width - width) / 2);

                g.setColor(trackFilledColor);
                g.fillRect(x, trackRect.y + (trackRect.height - delta_height), width , delta_height);

                g.setColor(trackNotFilledColor);
                g.fillRect(x, trackRect.y, width, trackRect.height - delta_height);
            }
        }

        /**
         * this is supposed to be empty
         */
        @Override
        public void paintFocus(Graphics g) {
        }

        @Override
        public void paintTicks(Graphics g) {
            g.setColor(decoColor);

            int x = tickRect.x;
            int y = trackRect.y;

            if (slider.getMajorTickSpacing() != 0) {
                int size = slider.getFont().getSize();
                for (int i = slider.getMinimum(); i <= slider.getMaximum(); i += slider.getMajorTickSpacing()) {
                    int offset = trackRect.height * i / slider.getMaximum();
                    g.drawArc(x, y + offset - (size/2), size, size, 90, 180);
                }
            }

            if (slider.getMinorTickSpacing() != 0) {
                for (int i = slider.getMinimum(); i <= slider.getMaximum(); i += slider.getMinorTickSpacing()) {
                    if (i % slider.getMajorTickSpacing() != 0) {
                        int offset = trackRect.height * i / slider.getMaximum();
                        g.drawLine(x, y + offset, x + (trackRect.width/3), y + offset);
                    }
                }
            }
        }

        @Override
        public <CP extends JmpGuiColorPalette> void updateColors(CP cp) {
            decoColor = cp.sliderDecoColor();
            thumbColor = cp.thumbColor();
            trackFilledColor = cp.trackFilledColor();
            trackNotFilledColor = cp.trackNotFilledColor();
        }
    }
}
