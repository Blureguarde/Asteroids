package asteroids.game;

import static asteroids.game.Constants.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import javax.swing.*;

/**
 * The area of the display in which the game takes place.
 */
@SuppressWarnings("serial")
public class Screen extends JPanel {
    private final static Font LEGEND_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 120);
    private final static Font UI_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
    
    /** Legend that is displayed across the screen */
    private String legend;

    /** Game controller */
    private Controller controller;

    /**
     * Creates an empty screen
     */
    public Screen (Controller controller)
    {
        this.controller = controller;
        legend = "";
        setPreferredSize(new Dimension(SIZE, SIZE));
        setMinimumSize(new Dimension(SIZE, SIZE));
        setBackground(Color.black);
        setForeground(Color.white);
        setFont(LEGEND_FONT);
        setFocusable(true);
    }

    /**
     * Set the legend
     */
    public void setLegend (String legend)
    {
        this.legend = legend;
    }
    
    /** Draw score UI */
    public void drawScore(Graphics2D g, Controller controller) {
        AttributedString score = new AttributedString(String.format("%04d", controller.getScore()));
        score.addAttribute(TextAttribute.FONT, UI_FONT);
        g.drawString(score.getIterator(), LABEL_HORIZONTAL_OFFSET, LABEL_VERTICAL_OFFSET);
    }
    
    /** Draw lives UI */
    public void drawLives() {
        
    }
    
    /**
     * Paint the participants onto this panel
     */
    @Override
    public void paintComponent(Graphics graphics) {
        // Use better resolution
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Do the default painting
        super.paintComponent(g);

        // Draw each participant in its proper place
        for (Participant p: controller) p.draw(g);
        
        // Draw the legend across the middle of the panel
        int size = g.getFontMetrics().stringWidth(legend);
        g.drawString(legend, (SIZE - size) / 2, SIZE / 2);
        
        drawScore(g, controller);
    }
}
