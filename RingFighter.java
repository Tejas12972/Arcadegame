/* Tejas B and Nischay S
 * Main RingFighter file for game launch 
 */
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Image;
import java.awt.Toolkit;


public class RingFighter {
    public static void main(String[] args) {
        System.out.println("Starting Main...");
        JFrame frame = new JFrame("Ring Fighter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        ImageIcon img = new ImageIcon("images/icon.png");
        frame.setIconImage(img.getImage());

        GameUI gameUI = new GameUI(frame);
        gameUI.initialize();

        frame.setVisible(true);
        System.out.println("Ring Fighter finished initializing.");
    }
}
