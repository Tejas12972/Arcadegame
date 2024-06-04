/* Tejas B and Nischay S
 * BackgroundPanel class for RingFighter
 */
import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String path) {
        System.out.println("Loading background image from: " + path);
        this.backgroundImage = Toolkit.getDefaultToolkit().createImage(path);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
